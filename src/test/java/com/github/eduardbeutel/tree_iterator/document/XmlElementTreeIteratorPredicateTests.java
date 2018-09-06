package com.github.eduardbeutel.tree_iterator.document;

import com.github.eduardbeutel.tree_iterator.core.XmlUtils;
import org.junit.Test;
import org.w3c.dom.Document;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

public class XmlElementTreeIteratorPredicateTests
{

    @Test
    public void when()
    {
        // given
        Document document = XmlUtils.createDocument(
                "<document>\n" +
                        "    <book id=\"1\" />\n" +
                        "    <book/>\n" +
                        "    <book id=\"2\" />\n" +
                        "</document>"
        );
        AtomicInteger firstCount = new AtomicInteger();
        AtomicInteger secondCount = new AtomicInteger();

        // when
        XmlElementTreeIterator.of(document)
                .when(e -> e.hasAttribute("id")).then(e -> firstCount.incrementAndGet())
                .when(e -> e.hasAttribute("id")).then(e -> secondCount.incrementAndGet())
                .execute()
        ;

        // then
        int expectedCount = 2;
        assertEquals(expectedCount, firstCount.get());
        assertEquals(expectedCount, secondCount.get());
    }

}
