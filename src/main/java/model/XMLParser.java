package model;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Class provides methods for writing and reading tasks into XML files
 */
public class XMLParser {
    private static final Logger LOGGER = Logger.getLogger(XMLParser.class);
    private static String filePath = "";
    private static String backupFilePath = "";
    private static int storageTime;
    private static URL schemaPath;
    private static long lasVisit;

    static {
        InputStream fis = null;
        Properties property = new Properties();

        try {
            fis = XMLParser.class.getClassLoader().getResourceAsStream("config.properties");
            property.load(fis);
            filePath = property.getProperty("savedListPath");
            backupFilePath = property.getProperty("backupListPath");
            schemaPath = XMLParser.class.getResource("/xmlValidator.xsd");
            String timeProperty = property.getProperty("storageTime");
            storageTime = Integer.valueOf(timeProperty) * 24 * 60 * 60;


        } catch (IOException e) {
            LOGGER.error("Exception", e);
        } finally {
            if (fis != null)
                try {
                    fis.close();
                } catch (IOException e) {
                    LOGGER.error("Exception", e);
                }
        }
    }

    /**
     * <strong>[Method - Getter]</strong>
     *
     * @return time of last application visit
     */
    public static long getLasVisit() {
        return lasVisit;
    }

    /**
     * Read data from XML files and using it makes new List of tasks
     *
     * @param taskList list, which is written tasks
     */
    public static void readXML(List<Task> taskList) {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document document;
        Task task;
        String title;
        String isActive;
        String isRepeat;
        Date startDate;
        Date endDate;
        int interval;

        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema;
            schema = schemaFactory.newSchema(schemaPath);
            Validator validator = schema.newValidator();
            builder = builderFactory.newDocumentBuilder();
            File file = new File(filePath);
            validator.validate(new StreamSource(file));
            if (file.length() != 0)
                document = builder.parse(file);
            else return;
            lasVisit = Long.valueOf(document.getElementsByTagName("list").item(0).getAttributes().item(0).getNodeValue());
            NodeList tasks = document.getElementsByTagName("task");
            int length = tasks.getLength();
            for (int i = 0; i < length; i++) {
                Element element = (Element) tasks.item(i);
                title = element.getElementsByTagName("title").item(0).getChildNodes().item(0).getNodeValue();
                isActive = element.getElementsByTagName("activity").item(0).getChildNodes().item(0).getNodeValue();
                isRepeat = element.getElementsByTagName("repetitive").item(0).getChildNodes().item(0).getNodeValue();
                startDate = new Date(Long.parseLong(element.getElementsByTagName("start_time").item(0).getChildNodes().item(0).getNodeValue()));
                if (isRepeat.equals("true")) {
                    endDate = new Date(Long.parseLong(element.getElementsByTagName("end_time").item(0).getChildNodes().item(0).getNodeValue()));
                    interval = Integer.parseInt(element.getElementsByTagName("interval").item(0).getChildNodes().item(0).getNodeValue());
                    task = new Task(title, startDate, endDate, interval);
                    if (isActive.equals("true"))
                        task.setActive(true);
                } else {
                    task = new Task(title, startDate);
                    if (isActive.equals("true"))
                        task.setActive(true);
                }
                if (!task.isOld(storageTime)) {
                    taskList.add(task);
                }
            }

        } catch (SAXException e) {
            LOGGER.error("Exception", e);
            try {
                FileChannel sourceChannel = new FileInputStream(filePath).getChannel();
                try {
                    FileChannel destChannel = new FileOutputStream(backupFilePath).getChannel();
                    try {
                        destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
                    } catch (IOException e1) {
                        LOGGER.error("Exception", e1);
                    } finally {
                        destChannel.close();
                    }
                } catch (IOException e2) {
                    LOGGER.error("Exception", e2);
                } finally {
                    sourceChannel.close();
                }
            } catch (IOException ex) {
                LOGGER.error("Exception", ex);
            }
        } catch (ParserConfigurationException e) {
            LOGGER.error("Exception", e);
        } catch (IOException e) {
            LOGGER.error("Exception", e);
        }

    }

    /**
     * Write tasks from taskList into XML file.
     *
     * @param taskList list, where tasks are stored.
     */
    public static void writeXML(List<Task> taskList) {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        Document document;

        try {
            documentBuilder = builderFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();

            Element listElement = document.createElement("list");
            listElement.setAttribute("lastVisit", String.valueOf(new Date().getTime()));
            document.appendChild(listElement);
            for (Task task : taskList) {
                Element taskElement = document.createElement("task");
                Element titleElement = document.createElement("title");
                Element isActiveElement = document.createElement("activity");
                Element isRepeatElement = document.createElement("repetitive");
                Element startDateElement = document.createElement("start_time");
                Element endDateElement = document.createElement("end_time");
                Element intervalElement = document.createElement("interval");

                titleElement.appendChild(document.createTextNode(task.getTitle()));
                isActiveElement.appendChild(document.createTextNode(String.valueOf(task.isActive())));
                isRepeatElement.appendChild(document.createTextNode(String.valueOf(task.isRepeated())));
                startDateElement.appendChild(document.createTextNode(String.valueOf(task.getStartTime().getTime())));
                if (task.isRepeated()) {
                    endDateElement.appendChild(document.createTextNode(String.valueOf(task.getEndTime().getTime())));
                    intervalElement.appendChild(document.createTextNode(String.valueOf(task.getRepeatInterval())));
                    listElement.appendChild(taskElement);
                    taskElement.appendChild(titleElement);
                    taskElement.appendChild(isActiveElement);
                    taskElement.appendChild(isRepeatElement);
                    taskElement.appendChild(startDateElement);
                    taskElement.appendChild(endDateElement);
                    taskElement.appendChild(intervalElement);
                } else {
                    listElement.appendChild(taskElement);
                    taskElement.appendChild(titleElement);
                    taskElement.appendChild(isActiveElement);
                    taskElement.appendChild(isRepeatElement);
                    taskElement.appendChild(startDateElement);
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamFile = new StreamResult(new File(filePath));
            transformer.transform(domSource, streamFile);

        } catch (ParserConfigurationException e) {
            LOGGER.error("Exception", e);
        } catch (TransformerConfigurationException e) {
            LOGGER.error("Exception", e);
        } catch (TransformerException e) {
            LOGGER.error("Exception", e);
        }
    }

}
