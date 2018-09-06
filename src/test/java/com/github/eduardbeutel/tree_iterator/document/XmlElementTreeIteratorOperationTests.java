package com.github.eduardbeutel.tree_iterator.document;

import com.github.eduardbeutel.tree_iterator.core.XmlUtils;
import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

public class XmlElementTreeIteratorOperationTests
{

    @Before
    public void beforeOperationTests()
    {
        XMLUnit.setIgnoreWhitespace(true);
    }

    @Test
    public void then()
    {
        // given
        Document document = XmlUtils.createDocument(
                "<document>\n" +
                        "    <book id=\"1\" />\n" +
                        "    <book/>\n" +
                        "    <book id=\"2\" />\n" +
                        "</document>"
        );

        // when
        XmlElementTreeIterator.of(document)
                .when(e -> "2".equals(e.getAttribute("id"))).then(e -> e.setTextContent("content"))
                .execute()
        ;

        // then
        Document expectedDocument = XmlUtils.createDocument(
                "<document>\n" +
                        "    <book id=\"1\" />\n" +
                        "    <book/>\n" +
                        "    <book id=\"2\">content</book>\n" +
                        "</document>"
        );
        XMLAssert.assertXMLEqual(expectedDocument, document);

    }

}
