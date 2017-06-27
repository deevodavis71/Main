package il.org.spartan.Leonidas.plugin;

import fluent.ly.note;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Reads the plugin.xml file,
 * Allows to read various values from it later.
 *
 * @author RoeiRaz
 * @since 31-03-17
 */
public class PluginDescriptorReader {

    private static final String PLUGIN_XML_URI = "META-INF/plugin.xml";
    private static final String ROOT_ELEMENT_NAME = "idea-plugin";
    private static final String ID_ELEMENT_NAME = "id";

    private static final ClassLoader classLoader = PluginDescriptorReader.class.getClassLoader();

    private static Document document;

    static {
        BufferedReader pluginXmlBuffer =
                new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream(PLUGIN_XML_URI)));
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new InputSource(pluginXmlBuffer));
        } catch (ParserConfigurationException | SAXException | IOException ¢) {
note.bug(¢);
        }
    }

    public static String getPluginId() {
        NodeList $ = document.getElementsByTagName(ID_ELEMENT_NAME);
        for (int ¢ = 0; ¢ < $.getLength(); ++¢)
			if (ROOT_ELEMENT_NAME.equals($.item(¢).getParentNode().getNodeName()))
				return $.item(¢).getTextContent();
        throw new RuntimeException("ID element wasn't found in plugin.xml");
    }
}
