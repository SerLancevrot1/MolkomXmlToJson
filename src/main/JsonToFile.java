package main;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class JsonToFile {

    private File findFile(){

        File file = null;
        try{
            file = new File("C:\\Users\\max\\Desktop\\InternetSale_383290_20201117142043.xml");
        }
        catch (Exception e){
            e.getMessage();
        }

        return file;
    }

    public StringBuilder parseXml(){

        File file = findFile();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        Document document = null;
        try {
            document = documentBuilder.parse(file);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder stringBuilder = new StringBuilder();


        Node node1 = document.getElementsByTagName("FILE_ID").item(0);
        stringBuilder.append(String.format("{\n\"FILE_ID\": \"%s\"," , node1.getTextContent()));


        // на случай нескольких HEADER, мало ли какой шаблон
        NodeList headerList = document.getElementsByTagName("HEADER");
        for (int i = 0; i < headerList.getLength(); i++) {
            //на 1 HEADER


            stringBuilder.append(String.format("\n\"HEADER\":{ " , node1.getTextContent()));

            NodeList childList = headerList.item(i).getChildNodes();
            for (int j = 0; j < childList.getLength(); j++) {
                // внутри одного HEADER. childNode == один параметр в тэге
                Node childNode = childList.item(j);
                // проверка на элемент чтобы избавиться от #text
                if (childNode.getNodeType() == Node.ELEMENT_NODE){
                    //чтобы ковычки закрывались корректно
                    if(childNode == childList.item(childList.getLength() - 2)){

                        stringBuilder.append(String.format("\n\"%s\": \"%s\" \n}, ",   childNode.getNodeName().trim(),  childNode.getTextContent()));
                        break;
                    }
                    // основной вывод
                    stringBuilder.append(String.format("\n\"%s\": \"%s\", ",   childNode.getNodeName().trim(),  childNode.getTextContent()));
                }

            }
        }

        // без бутылки не разберешься
        NodeList detailsList = document.getElementsByTagName("DETAILS");

        // для нескольких DETAILS
        for (int i = 0; i < detailsList.getLength(); i++) {
            NodeList detailList = detailsList.item(i).getChildNodes();
            // итерация нескольких DETAIL

            stringBuilder.append(String.format("\n\"DETAILS\":["));
            for (int j = 0; j < detailList.getLength(); j++) {
                Node oneDetailsNode = detailList.item(j);

                if (oneDetailsNode.getNodeType() == Node.ELEMENT_NODE){

                    NodeList detailNodes = oneDetailsNode.getChildNodes();

                    stringBuilder.append(String.format("\n {"));
                    //внутри одной DETAIL. АААААА, как же люблю for
                    for(int n = 1; n < detailNodes.getLength() ; n++ ) {

                        // detailAtribute == внетренний тег в одной DETAIL
                        Node detailAtribute = detailNodes.item(n);

                        if (detailNodes.item(n).getNodeType() == Node.ELEMENT_NODE){
                            if(detailAtribute == detailNodes.item(detailNodes.getLength() - 2)){
                                if(j == detailList.getLength() - 2 ) {
                                    if(i == detailsList.getLength()-1){
                                        // если последний элемент вообще
                                        stringBuilder.append(String.format("\n\"%s\": \"%s\" \n}\n]\n} ",   detailAtribute.getNodeName().trim(),  detailAtribute.getTextContent()));
                                        continue;
                                    }
                                    // если есть еще DETAILS
                                    stringBuilder.append(String.format("\n\"%s\": \"%s\" \n}\n], ",   detailAtribute.getNodeName().trim(),  detailAtribute.getTextContent()));
                                    continue;
                                }
                                //если последний элемент в DETAIL
                                stringBuilder.append(String.format("\n\"%s\": \"%s\" \n}, ",   detailAtribute.getNodeName().trim(),  detailAtribute.getTextContent()));
                                continue;
                            }
                            //вывод основных элементов
                            stringBuilder.append(String.format("\n\"%s\": \"%s\", ",   detailAtribute.getNodeName().trim(),  detailAtribute.getTextContent()));
                        }

                    }
                }

            }
        }



        return stringBuilder;

    }


}
