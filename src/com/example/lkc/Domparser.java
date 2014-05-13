package com.example.lkc;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.os.Bundle;
import android.renderscript.Element;
import android.widget.TextView;

public class Domparser extends Activity {

	TextView tv1;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		tv1 = (TextView) findViewById(R.id.textView1);

		try {

			InputStream is = this.getAssets().open("file.xml");

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory

			.newInstance();

			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

			Document doc = dBuilder.parse(is);

			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("item");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					tv1.setText(tv1.getText() + "\n\nName : "+ getValue("name", eElement) + "\n");

					tv1.setText(tv1.getText() + "Price : "

					+ getValue("price", eElement) + "\n");

					tv1.setText(tv1.getText() + "-----------------------");

				}

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	private static String getValue(String sTag, Element eElement) {

		NodeList nlList = ((Document) eElement).getElementsByTagName(sTag).item(0).getChildNodes();

		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue();

	}

}
