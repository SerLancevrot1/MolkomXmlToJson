package main;

public class main {
    public static void main(String[] args) {

        JsonToFile jsonToFile = new JsonToFile();
        StringBuilder stringBuilder = jsonToFile.parseXml();

        Sb sb = new Sb();
        sb.showSb(stringBuilder);
    }
}
