package com.mibaldi.retorss3.Rss;

import android.sax.Element;
import android.sax.ElementListener;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.sax.StartElementListener;
import android.util.Xml;

import com.mibaldi.retorss3.Models.Noticia;
import com.mibaldi.retorss3.Utils.DateFormatter;

import org.xml.sax.Attributes;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikelbalducieldiaz on 15/5/16.
 */
public class ParseadorRSSXML {
    private URL noticiasUrl;
    private Noticia noticiaActual;

    public ParseadorRSSXML(String url) {
        try {
            this.noticiasUrl = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Noticia> parse() {
        final List<Noticia> noticias = new ArrayList<>();
        RootElement rootBasic = new RootElement("rss");
        Element root = rootBasic.getChild("channel");
        Element noticia = root.getChild("item");
        noticia.setStartElementListener(new StartElementListener() {
            @Override
            public void start(Attributes attributes) {
                noticiaActual = new Noticia();
            }
        });
        noticia.setEndElementListener(new EndElementListener() {
            @Override
            public void end() {
                noticias.add(noticiaActual);
            }
        });
        noticia.getChild("title").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                noticiaActual.setTitle(body);
            }
        });
        noticia.getChild("http://search.yahoo.com/mrss/","content").setElementListener(new ElementListener() {
            @Override
            public void end() {

            }

            @Override
            public void start(Attributes attributes) {
                noticiaActual.setImage(attributes.getValue("url"));
            }
        });
        noticia.getChild("enclosure").setElementListener(new ElementListener() {
            @Override
            public void end() {

            }

            @Override
            public void start(Attributes attributes) {
                noticiaActual.setImage(attributes.getValue("url"));
            }
        });
        noticia.getChild("http://purl.org/rss/1.0/modules/content/","encoded").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {

                noticiaActual.setDescription(body);
            }
        });
        noticia.getChild("http://search.yahoo.com/mrss/","description").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                noticiaActual.setDescription(body);
            }
        });
        /*noticia.getChild("description").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {


                noticiaActual.setDescription(body);
            }
        });*/
        noticia.getChild("guid").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                noticiaActual.setUrl(body);
            }
        });
        noticia.getChild("pubDate").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                noticiaActual.setPubDate(DateFormatter.convertStringToDate(body));


            }
        });
        try {
            Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8, rootBasic.getContentHandler());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return noticias;
    }

    private InputStream getInputStream() {
        try {
            return noticiasUrl.openConnection().getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
