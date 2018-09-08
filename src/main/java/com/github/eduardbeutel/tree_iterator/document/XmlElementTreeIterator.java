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

    public static Conditions<Element> of(Document document)
    {
        return new XmlElementTreeIterator(document).getConditions();
    }

    @Override
    protected void iterate(Object object)
    {
        Element rootElement = ((Document) object).getDocumentElement();
        String rootId = getId(rootElement);
        iterateElement(rootElement, rootId, "/" + rootId);
    }

    @Override
    protected boolean isLeaf(Element element)
    {
        int nrChildren = element.getChildNodes().getLength();
        for (int i = 0; i < nrChildren; i++)
        {
            Node childNode = element.getChildNodes().item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) return false;
        }
        return true;
    }

    protected void iterateElement(Element element, String id, String path)
    {
        executeCommands(element, id, path);

        int nrChildren = element.getChildNodes().getLength();
        for (int i = 0; i < nrChildren; i++)
        {
            Node childNode = element.getChildNodes().item(i);
            if (childNode.getNodeType() != Node.ELEMENT_NODE) continue;
            Element childElement = (Element) childNode;

            String childId = getId(childElement);
            String childPath = path + "/" + childId;
            iterateElement(childElement, childId, childPath);
        }
    }

    protected String getId(Element element)
    {
        String id = element.getLocalName();
        if (id == null) throw new RuntimeException("Please use DocumentBuilderFactory.setNamespaceAware(true).");
        return id;
    }

}
