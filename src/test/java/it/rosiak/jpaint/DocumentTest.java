package it.rosiak.jpaint;

import it.rosiak.jpaint.document.Document;
import it.rosiak.jpaint.layout.ResizableCanvas;
import org.junit.Assert;
import org.junit.Test;

public class DocumentTest {

    @Test
    public void newDocumentShouldCreateOneLayer() throws Exception {
        ResizableCanvas canvas = new ResizableCanvas();
        Document doc = new Document();
        Assert.assertEquals(1, doc.getLayersCount());
    }


}