package by.bsuir.Dormitory.service.xml;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class StaxXmlFactory extends XMLFactory {

    public StaxXmlFactory(String resource) {
        super(resource);
    }

    @Override
    protected XMLParser createXMLParser() throws IOException, XMLStreamException {
        return new StAXParser(resource);
    }

    @Override
    public String getType() {
        return "StAX Parser";
    }
}
