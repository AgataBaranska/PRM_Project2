package com.example.prm_projct2


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prm_projct2.databinding.ActivityDisplayDataBinding
import com.example.prm_projct2.models.Item
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executors

class DisplayDataActivity : AppCompatActivity() {


    lateinit var binding:ActivityDisplayDataBinding
    private val myExecutor = Executors.newSingleThreadExecutor()
    private val myHandler = Handler(Looper.getMainLooper())
    lateinit var rvNews: RecyclerView
    val FEED_WORLD_URL = "https://feeds.skynews.com/feeds/rss/world.xml"
    lateinit var items:MutableList<Item>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rvNews = binding.rvNews
        items = mutableListOf()

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

            var title:String?= null
            var img:String?= null
            var description:String?= null
            var link:String?= null
            while(eventType != XmlPullParser.END_DOCUMENT){



                if(eventType == XmlPullParser.START_TAG){
                    if(xmlParser.name.equals("item")){
                        insideItem = true

                    }else if(xmlParser.name.equals("title")&&insideItem){
                        title = xmlParser.nextText()
                    }else if(xmlParser.name.equals("media:content")&&insideItem){
                        img = xmlParser.getAttributeValue(null,"url")
                    }else if(xmlParser.name.equals("description")&&insideItem){
                        description = xmlParser.nextText()
                    }else if(xmlParser.name.equals("link")&&insideItem){
                        link = xmlParser.nextText()
                    }

                }else if(eventType == XmlPullParser.END_TAG && xmlParser.name.equals("item")){
                    val item = Item(title,img,description,link)
                    items.add(item)
                    insideItem = false
                    link = null
                    img = null
                    description = null
                    title = null
                }
                eventType = xmlParser.next()
            }

            myHandler.post{
              val adapter = RecyclerAdapter(items)
                rvNews.adapter = adapter
                rvNews.layoutManager = LinearLayoutManager(this)
                rvNews.setHasFixedSize(true)
            }

        }
    }
}