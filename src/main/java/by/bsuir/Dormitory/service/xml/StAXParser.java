package by.bsuir.Dormitory.service.xml;


import by.bsuir.Dormitory.model.User;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class StAXParser implements XMLParser {
    private final XMLStreamWriter writer;

    public StAXParser(String resource) throws IOException, XMLStreamException {
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        writer = factory.createXMLStreamWriter(new FileWriter(resource));
    }

    @Override
    public void saveUsers(List<User> users) {
        try {
            writer.writeStartDocument();
            writer.writeStartElement("users");

            for (User user : users)
                writeUser(user);
            writer.writeEndElement();
            writer.writeEndDocument();
            writer.flush();
            writer.close();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }


    public void writeUser(User user) throws XMLStreamException {
        writer.writeStartElement("user");
        writer.writeAttribute("id", user.getUserId().toString());
        writeNode("lastName", user.getLastName());
        writeNode("firstName", user.getFirstName());
        writeNode("patronymic", user.getPatronymic());
        writeNode("email", user.getEmail());
        writeNode("username", user.getUsername());
        writeNode("registerDate", user.getRegisterDate().toString());
        writeNode("role", user.getRole().getType());
        writer.writeEndElement();
    }

    private void writeNode(String name, String value) throws XMLStreamException {
        writer.writeStartElement(name);
        writer.writeCharacters(value);
        writer.writeEndElement();
    }
}
