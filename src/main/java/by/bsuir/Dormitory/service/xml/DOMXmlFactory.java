package by.bsuir.Dormitory.service.xml;

import javax.xml.parsers.ParserConfigurationException;

public class DOMXmlFactory extends XMLFactory {
    public DOMXmlFactory(String resource) {
        super(resource);
    }

    @Override
    protected XMLParser createXMLParser() throws ParserConfigurationException {
        return new DOMParser(resource);
    }

    @Override
    public String getType() {
        return "DOM Parser";
    }
}
