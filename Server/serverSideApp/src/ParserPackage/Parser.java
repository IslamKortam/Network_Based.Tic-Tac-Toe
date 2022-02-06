/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParserPackage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

/**
 *
 * @author imkor
 */
public class Parser {
    
    private static GsonBuilder builder = new GsonBuilder();;
    public static Gson gson = builder.create();
}
