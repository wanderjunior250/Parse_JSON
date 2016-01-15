package com.exercicio.wander.parse_json;

/**
 * Created by Wander on 14/01/2016.
 */
public class JsonDados
{

    private String id;
    private String name;
    private String pwd;


    //construtor vazio
    public JsonDados ()
    {
    }//fim construtor

    //construtor com passagem de parametros
    public JsonDados(String id, String name, String pwd)
    {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
    }//fim construtor

    public void setId (String id)
    {
        this.id = id;
    }//fim metodo setId

    public String getId ()
    {
        return id;
    }//fim metodo getId

    public void setName (String name)
    {
        this.name = name;
    }//fim metodo setName

    public String getName ()
    {
        return name;
    }//fim metodo getId

    public void setPwd (String pwd)
    {
        this.pwd = pwd;
    }//fim metodo setId

    public String getPwd ()
    {
        return pwd;
    }//fim metodo getId



    public String toString ()
    {
        String item = String.format("ID = %s\nName = %s\nPwd = %s", id, name, pwd);
        return item;
    }//fim metodo toString
}//fim classe JsonDados
