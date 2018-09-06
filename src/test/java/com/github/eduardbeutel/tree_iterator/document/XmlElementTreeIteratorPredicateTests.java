package com.github.eduardbeutel.tree_iterator.document;

import com.github.eduardbeutel.tree_iterator.test.XmlUtils;
import org.junit.Test;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        List<String> firstResult = new ArrayList<>();
        List<String> secondResult = new ArrayList<>();

        // when
        XmlElementTreeIterator.of(document)
                .when(e -> e.hasAttribute("id")).then(e -> firstResult.add(e.getLocalName()))
                .when(e -> e.hasAttribute("id")).then(e -> secondResult.add(e.getLocalName()))
                .execute()
        ;

        // then
        List<String> expectedResult = Arrays.asList("book", "book");
        assertEquals(expectedResult, firstResult);
        assertEquals(expectedResult, secondResult);

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
        List<String> result = new ArrayList<>();

        // when
        XmlElementTreeIterator.of(document)
                .whenNot(e -> e.hasAttribute("id")).then(e -> result.add(e.getLocalName()))
                .execute()
        ;

        // then
        assertEquals(Arrays.asList("library", "book"), result);
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
        List<String> result = new ArrayList<>();

        // when
        XmlElementTreeIterator.of(document)
                .always().then(e -> result.add(e.getLocalName()))
                .execute()
        ;

        // then
        assertEquals(Arrays.asList("library", "book", "book", "book"), result);
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
        List<String> result = new ArrayList<>();

        // when
        XmlElementTreeIterator.of(document)
                .whenId("book1").then(e -> result.add(e.getLocalName()))
                .execute()
        ;

        // then
        assertEquals(Arrays.asList("book1", "book1"), result);
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
        List<String> result = new ArrayList<>();

        // when
        XmlElementTreeIterator.of(document)
                .whenId("book1").then(e -> result.add(e.getLocalName()))
                .execute()
        ;

        // then
        assertEquals(Arrays.asList("book1", "book1"), result);
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
        List<String> result = new ArrayList<>();

        // when
        XmlElementTreeIterator.of(document)
                .whenPath("/library/book/author").then(e -> result.add(e.getLocalName()))
                .execute()
        ;

        // then
        assertEquals(Arrays.asList("author", "author", "author"), result);
    }

    @Test
    public void whenIdMatches()
    {
        // given
        Document document = XmlUtils.createDocument(
                "<library>\n" +
                        "    <my-book/>\n" +
                        "    <book/>\n" +
                        "    <not-a-Book/>\n" +
                        "</library>"
        );
        List<String> result = new ArrayList<>();

        // when
        XmlElementTreeIterator.of(document)
                .whenIdMatches(".*book.*").then(e -> result.add(e.getLocalName()))
                .execute()
        ;

        // then
        assertEquals(Arrays.asList("my-book", "book"), result);
    }

    @Test
    public void whenPathMatches()
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
        List<String> result = new ArrayList<>();

        // when
        XmlElementTreeIterator.of(document)
                .whenPathMatches("/.*/author").then(e -> result.add(e.getLocalName()))
                .execute()
        ;

        // then
        assertEquals(Arrays.asList("author", "author", "author"), result);
    }

    @Test
    public void whenRoot()
    {
        // given
        Document document = XmlUtils.createDocument(
                "<library>\n" +
                        "    <book>\n" +
                        "        <title />\n" +
                        "    </book>\n" +
                        "</library>"
        );
        List<String> result = new ArrayList<>();

        // when
        XmlElementTreeIterator.of(document)
                .whenRoot().then(e -> result.add(e.getLocalName()))
                .execute()
        ;

        // then
        assertEquals(Arrays.asList("library"), result);
    }

    @Test
    public void whenLeaf()
    {
        // given
        Document document = XmlUtils.createDocument(
                "<library>\n" +
                        "    <book>\n" +
                        "        <title />\n" +
                        "        <author />\n" +
                        "    </book>\n" +
                        "</library>"
        );
        List<String> result = new ArrayList<>();

        // when
        XmlElementTreeIterator.of(document)
                .whenLeaf().then(e -> result.add(e.getLocalName()))
                .execute()
        ;

        // then
        assertEquals(Arrays.asList("title", "author"), result);
    }

    @Test
    public void whenNotLeaf()
    {
        // given
        Document document = XmlUtils.createDocument(
                "<library>\n" +
                        "    <book>\n" +
                        "        <title />\n" +
                        "        <author />\n" +
                        "    </book>\n" +
                        "</library>"
        );
        List<String> result = new ArrayList<>();

        // when
        XmlElementTreeIterator.of(document)
                .whenNotLeaf().then(e -> result.add(e.getLocalName()))
                .execute()
        ;

        // then
        assertEquals(Arrays.asList("library", "book"), result);
    }


}
