package org.jbehave.core.io.rest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.jbehave.core.io.ResourceLoader;

/**
 * Implementation that writes to filesystem the imported resources, using the
 * target file path and extension specified.
 * 
 * The importer requires an instance of a {@link ResourceIndexer} and of a
 * {@link ResourceLoader}.
 */
public class ImportToFilesystem implements ResourceImporter {

    private final ResourceIndexer indexer;
    private final ResourceLoader loader;
    private final String targetPath;
    private final String targetExt;

    public ImportToFilesystem(ResourceIndexer indexer, ResourceLoader loader, String targetPath, String targetExt) {
        this.indexer = indexer;
        this.loader = loader;
        this.targetPath = targetPath;
        this.targetExt = targetExt;
    }

    public void importResources(String sourcePath) {
        Map<String, Resource> index = indexer.indexResources(sourcePath);
        loadResources(index);
        writeResources(index, targetPath, targetExt);
    }

    private void loadResources(Map<String, Resource> index) {
        for (String name : index.keySet()) {
            Resource resource = index.get(name);
            String text = loader.loadResourceAsText(resource.getURI());
            resource.setText(text);
        }
    }

    private void writeResources(Map<String, Resource> index, String targetPath, String targetExt) {
        for (String name : index.keySet()) {
            Resource resource = index.get(name);
            File file = new File(targetPath, name + targetExt);
            writeResource(resource, file);
        }
    }

    private void writeResource(Resource resource, File file) {
        try {
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);
            if (resource.hasText()) {
                writer.write(resource.getText());
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to write resource " + resource + " to file " + file, e);
        }
    }

}