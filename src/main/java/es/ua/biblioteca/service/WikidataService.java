package es.ua.biblioteca.service;

import java.io.ByteArrayOutputStream;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.springframework.stereotype.Service;

@Service
public class WikidataService {
	
	public static String sparqlRepository = "https://query.wikidata.org/sparql";

	public String getAuthors(int num) {
		
		String sparqlQuery = "PREFIX wdt: <http://www.wikidata.org/prop/direct/> "
				+ "PREFIX wikibase: <http://wikiba.se/ontology#> "
				+ "PREFIX bd: <http://www.bigdata.com/rdf#> "
				+ "SELECT DISTINCT ?autor ?autorLabel "
				+ "WHERE { "
				+ "    ?autor wdt:P2799 ?idbvmc. "
				+ "    SERVICE wikibase:label { bd:serviceParam wikibase:language \"es\" } "
				+ "} LIMIT " + num;
		
		Query query = QueryFactory.create(sparqlQuery); 
        QueryExecution qExe = QueryExecutionFactory.sparqlService( sparqlRepository, query );
        ResultSet results = qExe.execSelect();
        
        // write to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ResultSetFormatter.outputAsJSON(outputStream, results);

        // and turn that into a String
        String json = new String(outputStream.toByteArray());
       
        
        return json;
	}
}
