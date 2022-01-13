package com.example.prm_projct2


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.prm_projct2.databinding.ActivityDisplayDataBinding
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executors

class DisplayDataActivity : AppCompatActivity() {

    private lateinit var links: MutableList<String>
    lateinit var binding:ActivityDisplayDataBinding
    private val myExecutor = Executors.newSingleThreadExecutor()
    private val myHandler = Handler(Looper.getMainLooper())
    lateinit var rvNews: ListView
    val FEED_WORLD_URL = "https://feeds.skynews.com/feeds/rss/world.xml"
    lateinit var titles:MutableList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rvNews = binding.rvNews
        titles = mutableListOf()
        links = mutableListOf()
        parseDataInBackground()

    }

    private fun downloadUrl(urlString: String): InputStream? {
        val url = URL(urlString)
        return url.openConnection().getInputStream()
    }

    private fun parseDataInBackground(){

        myExecutor.execute{

            val factory =  XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = false
            val xmlParser = factory.newPullParser()
            xmlParser.setInput( downloadUrl(FEED_WORLD_URL), "UTF_8")

            var insideItem = false

            var eventType = xmlParser.eventType

            while(eventType != XmlPullParser.END_DOCUMENT){

                println(xmlParser.name)
                if(eventType == XmlPullParser.START_TAG){

                    if(xmlParser.name.equals("item")){
                        insideItem = true
                    }else if(xmlParser.name.equals("title")&&insideItem){

                        titles.add(xmlParser.nextText())

                    }else if(xmlParser.name.equals("link")&&insideItem){

                        links.add(xmlParser.nextText())
                    }
                }else if(eventType == XmlPullParser.END_TAG && xmlParser.name.equals("item")){
                    insideItem = false
                }
                eventType = xmlParser.nextTag()
            }


            myHandler.post{
               val adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, titles)
                rvNews.adapter = adapter

            }

        }
    }
}