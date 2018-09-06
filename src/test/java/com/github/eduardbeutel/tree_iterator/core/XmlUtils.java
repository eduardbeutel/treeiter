package com.github.eduardbeutel.tree_iterator.core;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

public class XmlUtils
{

    public static Document createDocument(String xml)
    {
        try
        {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            return db.parse(is);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
