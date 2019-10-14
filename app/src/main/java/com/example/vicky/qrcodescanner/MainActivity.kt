package com.example.vicky.qrcodescanner

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Document
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_scan.setOnClickListener {
            val scanner = IntentIntegrator(this)
            scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            scanner.setBeepEnabled(false)

            scanner.initiateScan()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null) {
                if (result.getContents() == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                } else {
                    val doc: Document? = convertStringToXMLDocument(result.contents)
                    //convertStringToXMLDocument(doc)
                    var matricula =doc?.firstChild?.firstChild?.firstChild?.nodeValue
                    xml.text="${matricula?.subSequence(0, 8)}"

                }
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    private fun convertStringToXMLDocument(xmlString:String): Document? {
        val factory: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
        var builder: DocumentBuilder? = null
        try { //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder()
            //Parse the content to Document object
            return builder.parse(InputSource(StringReader(xmlString)))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


    /*try {

        val jsonObject = XML.to


        val obj  = JSONObject(result.getContents())
        xml.text=obj.getString("Matricula")
    }catch (e: JSONException){
        e.printStackTrace()

    }*/

}

