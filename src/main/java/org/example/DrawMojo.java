package org.example;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

@Mojo(name = "draw", defaultPhase = LifecyclePhase.COMPILE)
public class DrawMojo extends AbstractMojo {
    /*
    @Parameter(property = "scope")
    String scope;
    */


    private final List<String> art = Arrays.asList("bears", "cat", "fish");

    public String randomGenerateArt(String name) {
        Random random = new Random();
        return art.get(random.nextInt(3));
    }
    private  String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

    public String view(String name) {
        String data=null;
        try {
            if (name.isEmpty())
                name=randomGenerateArt(name);

            else if (art.indexOf(name) ==-1)
                throw new IOException();

            ClassLoader classLoader = DrawMojo.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(name + ".txt");
            data = readFromInputStream(inputStream);


        } catch (IOException e) {
            getLog().info("No such object!"); }
        return data;
    }
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        DrawMojo animalArt = new DrawMojo();
        getLog().info("Choose which ascii art to draw: ");
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        getLog().info(animalArt.view(s));
    }
}
