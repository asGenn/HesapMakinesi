package com.example.hesapmakinesi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hesapmakinesi.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        binding.hesap.setText("");
        binding.sonuc.setText("");
        setContentView(view);

    }

    public void getClıcked(View view) {

        String hesaplanacaklar = "";


        binding.btn0.getId();
        switch (view.getId()) {
            case R.id.btn_0:

                binding.hesap.getText();
                hesaplanacaklar = binding.hesap.getText() +"0";
                break;
            case  R.id.btn_1:
                hesaplanacaklar = binding.hesap.getText() + "1";
                break;
            case  R.id.btn_2:
                hesaplanacaklar = binding.hesap.getText() + "2";
                break;
            case  R.id.btn_3:
                hesaplanacaklar = binding.hesap.getText() + "3";
                break;
            case  R.id.btn_4:
                hesaplanacaklar = binding.hesap.getText() + "4";
                break;
            case  R.id.btn_5:
                hesaplanacaklar = binding.hesap.getText() + "5";
                break;
            case  R.id.btn_6:
                hesaplanacaklar = binding.hesap.getText() + "6";
                break;
            case  R.id.btn_7:
                hesaplanacaklar = binding.hesap.getText() + "7";
                break;
            case  R.id.btn_8:
                hesaplanacaklar = binding.hesap.getText() + "8";
                break;
            case  R.id.btn_9:
                hesaplanacaklar = binding.hesap.getText() + "9";
                break;
            case  R.id.btn_virgul:
                hesaplanacaklar = replaceWith(hesaplanacaklar,"." );
                break;
            case  R.id.btn_toplama:
                if(!binding.sonuc.getText().toString().isEmpty()){
                    hesaplanacaklar = binding.sonuc.getText().toString() + "+";
                    binding.sonuc.setText("");
                }else {
                    hesaplanacaklar = replaceWith(hesaplanacaklar,"+" );
                }
                break;
            case  R.id.btn_carpma:
                if(!binding.sonuc.getText().toString().isEmpty()){
                    hesaplanacaklar = binding.sonuc.getText().toString() + "*";
                    binding.sonuc.setText("");
                }else {
                    hesaplanacaklar = replaceWith(hesaplanacaklar,"*" );
                }

                break;
            case  R.id.btn_cikarma:
                if(!binding.sonuc.getText().toString().isEmpty()){
                    hesaplanacaklar = binding.sonuc.getText().toString()+ "-";
                    binding.sonuc.setText("");
                }else {
                    hesaplanacaklar = replaceWith(hesaplanacaklar,"-" );
                }
                break;
            case  R.id.btn_bolme:
                if(!binding.sonuc.getText().toString().isEmpty()){
                    hesaplanacaklar = binding.sonuc.getText().toString() + "/";
                    binding.sonuc.setText("");
                }else {
                    hesaplanacaklar = replaceWith(hesaplanacaklar,"/" );
                }

                break;
            case  R.id.btn_ac:
                binding.hesap.setText("");
                binding.sonuc.setText("");
                break;
            case  R.id.btn_yuzde:
                Toast.makeText(this, "Bu özellik yapım aşamasında", Toast.LENGTH_SHORT).show();

                break;
            case  R.id.btn_sil:
                hesaplanacaklar = binding.hesap.getText().toString();
                hesaplanacaklar = hesaplanacaklar.substring(0,hesaplanacaklar.length() -1);

                break;

        }
        binding.hesap.setText(hesaplanacaklar);
        setSonuc();

    }
    public String  replaceWith(String expression,String op){
        expression = binding.hesap.getText().toString();
        if(!expression.isEmpty()){
            String lastChar  = String.valueOf(expression.charAt(expression.length()-1));
            if(lastChar.equals("*") || lastChar.equals("/") ||lastChar.equals("+") ||lastChar.equals("-") ||lastChar.equals(".")){
                expression = expression.substring(0,expression.length() -1);
                expression = expression+op;
                return  expression;
            }
            else {
                expression = expression+op;
                return expression;
            }

        }
        return expression;

    }

    public void setSonuc(){
        binding.btnEsittir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                parseString(binding.hesap.getText().toString());
            }
        });
    }

    public void parseString(String expression){
        String[] tokens = expression.split("(?<=[-+*/])|(?=[-+*/])");
        if(!(tokens[tokens.length -1].equals("*") || tokens[tokens.length -1].equals("-")||tokens[tokens.length -1].equals("/")||tokens[tokens.length -1].equals("+")||tokens[tokens.length -1].equals(".") ||tokens[tokens.length -1].equals("") )){
            ArrayList<Double> integerArrayList = new ArrayList<>();
            ArrayList<String> strings = new ArrayList<>();
            for (String token : tokens) {
                if(token.matches("\\d+(\\.\\d+)?")){
                    integerArrayList.add(Double.parseDouble(token));
                }else{
                    strings.add(token);
                }
            }
            hesaplaCarpmaVeBolme(strings,integerArrayList);
            hesaplaToplamaVeBolme(strings,integerArrayList);

            binding.sonuc.setText(integerArrayList.get(0).toString());

        }
    }
    public void hesaplaCarpmaVeBolme(ArrayList<String> strings,ArrayList<Double> doubles){
        for(int i= 0; i<strings.size();i++ ){

            if(strings.get(i).equals("*")){
                double a = doubles.get(i) * doubles.get(i+1);
                doubles.remove(i);
                doubles.remove(i);
                doubles.add(i,a);
                strings.remove(i);
                hesaplaCarpmaVeBolme(strings,doubles);
            } else if (strings.get(i).equals("/")) {
                double a = doubles.get(i) / doubles.get(i+1);
                doubles.remove(i);
                doubles.remove(i);
                doubles.add(i,a);
                strings.remove(i);
                hesaplaCarpmaVeBolme(strings,doubles);
            }
        }

    }
    public void hesaplaToplamaVeBolme(ArrayList<String> strings,ArrayList<Double> doubles){
        for(int i= 0; i<strings.size();i++ ){

            if(strings.get(i).equals("+")){
                double a = doubles.get(i) + doubles.get(i+1);
                doubles.remove(i);
                doubles.remove(i);
                doubles.add(i,a);
                strings.remove(i);
                hesaplaToplamaVeBolme(strings,doubles);
            } else if (strings.get(i).equals("-")) {
                if(doubles.size() == 2 && strings.size() ==2){
                    double a = - doubles.get(0) -  doubles.get(1);
                    doubles.clear();
                    strings.clear();
                    doubles.add(0,a);
                    hesaplaToplamaVeBolme(strings,doubles);

                }else{
                    double a = doubles.get(i) - doubles.get(i+1);
                    doubles.remove(i);
                    doubles.remove(i);
                    doubles.add(i,a);
                    strings.remove(i);
                    hesaplaToplamaVeBolme(strings,doubles);

                }

            }

        }

    }
}