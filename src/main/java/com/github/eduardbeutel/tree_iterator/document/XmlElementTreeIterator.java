package com.github.eduardbeutel.tree_iterator.document;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XmlElementTreeIterator extends AbstractDocumentTreeIterator<Document, Element>
{

    private XmlElementTreeIterator(Document document)
    {
        super(document);
    }

    public static Predicates<Element> of(Document document)
    {
        return new XmlElementTreeIterator(document)
                .getPredicates();
    }

    @Override
    protected void iterate(Object object)
    {
        if (object instanceof Document)
        {
            iterateElement(((Document) object).getDocumentElement());
        }
        else if (object instanceof Element)
        {
            iterateElement((Element) object);
        }
    }

    protected void iterateElement(Element element)
    {
        executeOn(element);
        // iterate over child elements
        int nrChildren = element.getChildNodes().getLength();
        for (int i = 0; i < nrChildren; i++)
        {
            Node childNode = element.getChildNodes().item(i);
            if (childNode.getNodeType() != Node.ELEMENT_NODE) continue;
            Element childElement = (Element) childNode;
            iterate(childElement);
        }
    }

    private void executeOn(Element element)
    {
        for (PredicateOperationPair<Element> pair : getAllPairs())
        {
            getExecutor().execute(pair, element);
        }
    }

}
