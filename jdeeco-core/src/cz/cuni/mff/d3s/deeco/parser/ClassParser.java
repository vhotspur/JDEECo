package cz.cuni.mff.d3s.deeco.parser;

import java.util.List;

import cz.cuni.mff.d3s.jdeeco.core.model.parsedmetadata.ParsedModel;
import cz.cuni.mff.d3s.jdeeco.core.model.parsedmetadata.ParsedmodelFactory;

public class ClassParser implements Parser {
	List<Class> classes;
	ParsedModel model;
	ParsedmodelFactory factory;
	
	public ClassParser(List<Class> classes, ParsedmodelFactory factory) 
	{
		this.classes = classes;
		this.factory = factory;
	}

	@Override
	public ParsedModel getModel() {
		if (model == null)
			parse();
		return model;
	}
	
	private void parse() {		
		model = factory.createParsedModel();
		
	}
	
	

}
