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

public class ReadXml {

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

    public void parseXml(){
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



        Node node1 = document.getElementsByTagName("FILE_ID").item(0);
        System.out.printf("{\n\"FILE_ID\": \"%s\", \n" , node1.getTextContent());



        //System.out.println(document.getDocumentElement().getNodeName());

        NodeList nodeListHead = document.getElementsByTagName("HEADER");
        Node node = nodeListHead.item(0);


            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) node;
                System.out.printf("\"%s\": { \n" , node.getNodeName());

                System.out.printf("\"INVOICE\": \"%s\", \n", eElement.getElementsByTagName("INVOICE").item(0).getTextContent());
                System.out.printf("\"DATE\": \"%s\", \n" , eElement.getElementsByTagName("DATE").item(0).getTextContent());
                System.out.printf("\"ORDER_TYPE\": \"%s\", \n" , eElement.getElementsByTagName("ORDER_TYPE").item(0).getTextContent());
                System.out.printf("\"EST_SHIP_DATE\": \"%s\", \n" , eElement.getElementsByTagName("EST_SHIP_DATE").item(0).getTextContent());
                System.out.printf("\"SHIPMENT_METHOD\": \"%s\", \n" , eElement.getElementsByTagName("SHIPMENT_METHOD").item(0).getTextContent());
                System.out.printf("\"DELYVERY_SERVICE\": \"%s\", \n" , eElement.getElementsByTagName("DELYVERY_SERVICE").item(0).getTextContent());
                System.out.printf("\"AmountwVAT\": \"%s\", \n" , eElement.getElementsByTagName("AmountwVAT").item(0).getTextContent());
                System.out.printf("\"PaymentMethod\": \"%s\", \n" , eElement.getElementsByTagName("PaymentMethod").item(0).getTextContent());
                System.out.printf("\"Phone\": \"%s\", \n" , eElement.getElementsByTagName("Phone").item(0).getTextContent());

            }


        NodeList nodeListDetal = document.getElementsByTagName("DETAIL");

        for (int i =0; i < nodeListDetal.getLength(); i++){

            // длинна -1 без запятой
            Node nodeDetal = nodeListDetal.item(i);

            //System.out.println("\nCurrent Element :" + node.getNodeName());

            if (nodeDetal.getNodeType() == Node.ELEMENT_NODE)  {

                Element eElement = (Element) nodeDetal;

                System.out.println("N_STR : " + eElement.getElementsByTagName("N_STR").item(0).getTextContent());
                System.out.println("STOCK_NUMBER : " + eElement.getElementsByTagName("STOCK_NUMBER").item(0).getTextContent());
                System.out.println("STOCK_NAME : " + eElement.getElementsByTagName("STOCK_NAME").item(0).getTextContent());
                System.out.println("MeasureUnit : " + eElement.getElementsByTagName("MeasureUnit").item(0).getTextContent());
                System.out.println("UnitPrice : " + eElement.getElementsByTagName("UnitPrice").item(0).getTextContent());
                System.out.println("UnitVAT : " + eElement.getElementsByTagName("UnitVAT").item(0).getTextContent());
                System.out.println("PRICE : " + eElement.getElementsByTagName("PRICE").item(0).getTextContent());
                System.out.println("LinePrice : " + eElement.getElementsByTagName("LinePrice").item(0).getTextContent());
                System.out.println("LineVAT : " + eElement.getElementsByTagName("LineVAT").item(0).getTextContent());
                System.out.println("SUM : " + eElement.getElementsByTagName("SUM").item(0).getTextContent());
                System.out.println("LineDiscount : " + eElement.getElementsByTagName("LineDiscount").item(0).getTextContent());
                System.out.println("QTYEXPECTED : " + eElement.getElementsByTagName("QTYEXPECTED").item(0).getTextContent());
                System.out.println("LineAmount : " + eElement.getElementsByTagName("LineAmount").item(0).getTextContent());

            }
        }

    }

    public void anatherParser(){

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


        Node node1 = document.getElementsByTagName("FILE_ID").item(0);
        System.out.printf("{\n\"FILE_ID\": \"%s\"," , node1.getTextContent());
        System.out.printf("\n\"HEADER\":{ " , node1.getTextContent());

        // на случай нескольких HEADER, мало ли какой шаблон
        NodeList headerList = document.getElementsByTagName("HEADER");
        for (int i = 0; i < headerList.getLength(); i++) {
            //на 1 HEADER
            NodeList childList = headerList.item(i).getChildNodes();
            for (int j = 0; j < childList.getLength(); j++) {
                // внутри одного HEADER. childNode == один параметр в тэге
                Node childNode = childList.item(j);
                // проверка на элемент чтобы избавиться от #text
                if (childNode.getNodeType() == Node.ELEMENT_NODE){
                    //чтобы ковычки закрывались корректно
                    if(childNode == childList.item(childList.getLength() - 2)){
                        System.out.printf("\n\"%s\": \"%s\" \n}, ",   childNode.getNodeName().trim(),  childNode.getTextContent());
                        break;
                    }
                    // основной вывод
                    System.out.printf("\n\"%s\": \"%s\", ",   childNode.getNodeName().trim(),  childNode.getTextContent());
                }
                
            }
        }

        // без бутылки не разберешься
        NodeList detailsList = document.getElementsByTagName("DETAILS");
        System.out.printf("\n\"DETAILS\":[");
        // для нескольких DETAILS
        for (int i = 0; i < detailsList.getLength(); i++) {
            NodeList detailList = detailsList.item(i).getChildNodes();
            // итерация нескольких DETAIL
            for (int j = 0; j < detailList.getLength(); j++) {
                Node oneDetailsNode = detailList.item(j);

                if (oneDetailsNode.getNodeType() == Node.ELEMENT_NODE){

                    NodeList detailNodes = oneDetailsNode.getChildNodes();
                    System.out.printf("\n {");
                    //внутри одной DETAIL. АААААА, как же люблю for
                    for(int n = 1; n < detailNodes.getLength() ; n++ ) {

                        Node detailAtribute = detailNodes.item(n);

                        if (detailNodes.item(n).getNodeType() == Node.ELEMENT_NODE){
                            if(detailAtribute == detailNodes.item(detailNodes.getLength() - 2)){
                                if(j == detailList.getLength() - 2){
                                    System.out.printf("\n\"%s\": \"%s\" \n}\n}\n] ",   detailAtribute.getNodeName().trim(),  detailAtribute.getTextContent());
                                    continue;
                                }
                                System.out.printf("\n\"%s\": \"%s\" \n}, ",   detailAtribute.getNodeName().trim(),  detailAtribute.getTextContent());
                                continue;
                            }
                            System.out.printf("\n\"%s\": \"%s\", ",   detailAtribute.getNodeName().trim(),  detailAtribute.getTextContent());
                        }

                    }
                }

            }
        }
    }

}
