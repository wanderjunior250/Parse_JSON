package com.exercicio.wander.parse_json;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {


    // referencia da lista
    ListView lista;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //chamada para o metodo que consome o JSON
        new JSONtask().execute("https://s3-sa-east-1.amazonaws.com/pontotel-docs/data.json");

        // cria a lista e o ArrayAdapter para inserir os dados
        lista = (ListView)findViewById(R.id.lista);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        lista.setAdapter(arrayAdapter);



    }//fim metodo onCreate


    //classe interna que consome o JSON
    public class JSONtask extends AsyncTask<String, String, String[]> {



        @Override
        protected String[] doInBackground(String... params) {


            HttpURLConnection coneção = null;
            BufferedReader leitor = null;


            try {

                //passagem da url como parametro para o download do JSON
                URL url = new URL(params[0]);
                coneção = (HttpURLConnection) url.openConnection();
                coneção.connect();

                InputStream stream = coneção.getInputStream();

                leitor = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();


                String linha = "";
                while ((linha = leitor.readLine()) != null) {
                    buffer.append(linha);
                }// fim while superior

                String finalJson = buffer.toString();

                //cria os objetos JSON e declara um vetor para armazenar os dados
                JSONObject jsonObject = new JSONObject(finalJson);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                JsonDados dados[] = new JsonDados[jsonArray.length()];

                /*
                 *vetor de String que armazenará todos os dados;
                 *cada indice do vetor armazenará todos os dados formatados pelo metodo
                 * toString da classe JsonDados
                 */
                String[] resultado = new String[jsonArray.length()];

                //loop para correr entre os dados do JSONArray
                for (int x = 0; x < jsonArray.length(); x++)
                {
                    JSONObject finalObject = jsonArray.getJSONObject(x);
                    String id= finalObject.getString("id");
                    String name = finalObject.getString("name");
                    String pwd = finalObject.getString("pwd");
                    dados[x] = new JsonDados(id, name, pwd);  //cria um novo obj JsonDados para armazenar o conteudo extraido
                    resultado[x] = dados[x].toString();

                }//fim for superior


                return resultado;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                //testa a coneção e fecha o leitor caso sejam nulos
                if (coneção != null)
                    coneção.disconnect();
                try {
                    if (leitor != null)
                        leitor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }// fim try catch finally
            return null;
        }//fim metodo doInBackground

        @Override
        protected void onPostExecute(String[] resultado)
        {
            super.onPostExecute(resultado);

            arrayAdapter.addAll(resultado); //adiciona cada elemento do vetor resultado em um item na lista

        }//fim metodo onPostExecute
    }// fim classe interna JSONTask

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}//fim clase MainActivity


