package by.bsuir.Dormitory.service.xml;

import by.bsuir.Dormitory.model.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class DOMParser implements XMLParser {
    private final Document document;
    private final String resource;

    public DOMParser(String resource) throws ParserConfigurationException {
        this.resource = resource;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setIgnoringComments(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.newDocument();
    }

    @Override
    public void saveUsers(List<User> users) {
        Element element = document.createElement("users");
        document.appendChild(element);

        for (User user : users)
            writeUser(element, user);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(resource));
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public void writeUser(Element parent, User user) {
        Element element = document.createElement("user");
        parent.appendChild(element);

        element.setAttribute("id", user.getUserId().toString());
        writeNode(element, "lastName", user.getLastName());
        writeNode(element, "firstName", user.getFirstName());
        writeNode(element, "patronymic", user.getPatronymic());
        writeNode(element, "email", user.getEmail());
        writeNode(element, "username", user.getUsername());
        writeNode(element, "registerDate", user.getRegisterDate().toString());
        writeNode(element, "role", user.getRole().getType());
    }

    private void writeNode(Element parent, String name, String value) {
        Element element = document.createElement(name);
        element.appendChild(document.createTextNode(value));
        parent.appendChild(element);
    }

}
