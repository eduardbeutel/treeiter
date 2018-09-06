package com.github.eduardbeutel.tree_iterator.document;

import com.github.eduardbeutel.tree_iterator.test.XmlUtils;
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
                "<library>\n" +
                        "    <book id=\"1\" />\n" +
                        "    <book/>\n" +
                        "    <book id=\"2\" />\n" +
                        "</library>"
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

    @Test
    public void whenNot()
    {
        // given
        Document document = XmlUtils.createDocument(
                "<library>\n" +
                        "    <book id=\"1\" />\n" +
                        "    <book/>\n" +
                        "    <book id=\"2\" />\n" +
                        "</library>"
        );
        AtomicInteger count = new AtomicInteger();

        // when
        XmlElementTreeIterator.of(document)
                .whenNot(e -> e.hasAttribute("id")).then(e -> count.incrementAndGet())
                .execute()
        ;

        // then
        assertEquals(2, count.get()); // document & book
    }

    @Test
    public void always()
    {
        // given
        Document document = XmlUtils.createDocument(
                "<library>\n" +
                        "    <book id=\"1\" />\n" +
                        "    <book/>\n" +
                        "    <book id=\"2\" />\n" +
                        "</library>"
        );
        AtomicInteger count = new AtomicInteger();

        // when
        XmlElementTreeIterator.of(document)
                .always().then(e -> count.incrementAndGet())
                .execute()
        ;

        // then
        assertEquals(4, count.get()); // document & book
    }

    @Test
    public void whenId()
    {
        // given
        Document document = XmlUtils.createDocument(
                "<library>\n" +
                        "    <book1/>\n" +
                        "    <book2/>\n" +
                        "    <book1/>\n" +
                        "</library>"
        );
        AtomicInteger count = new AtomicInteger();

        // when
        XmlElementTreeIterator.of(document)
                .whenId("book1").then(e -> count.incrementAndGet())
                .execute()
        ;

        // then
        assertEquals(2, count.get());
    }

    @Test
    public void whenId_xmlWithNamespace()
    {
        // given
        Document document = XmlUtils.createDocument(
                "<m:document xmlns:m=\"http://my.namespace.com\">\n" +
                        "    <m:book1/>\n" +
                        "    <m:book2/>\n" +
                        "    <m:book1/>\n" +
                        "</m:document>"
        );
        AtomicInteger count = new AtomicInteger();

        // when
        XmlElementTreeIterator.of(document)
                .whenId("book1").then(e -> count.incrementAndGet())
                .execute()
        ;

        // then
        assertEquals(2, count.get());
    }

    @Test
    public void whenPath()
    {
        // given
        Document document = XmlUtils.createDocument(
                "<library>\n" +
                        "    <book>\n" +
                        "        <title />\n" +
                        "        <author />\n" +
                        "        <author />\n" +
                        "        <author />\n" +
                        "    </book>\n" +
                        "</library>"
        );
        AtomicInteger count = new AtomicInteger();

        // when
        XmlElementTreeIterator.of(document)
                .whenPath("/library/book/author").then(e -> count.incrementAndGet())
                .execute()
        ;

        // then
        assertEquals(3, count.get());
    }


}
