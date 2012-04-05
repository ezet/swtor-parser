package swtor.parser;

import java.io.IOException;

import swtor.parser.exception.LogParserException;
import swtor.parser.filter.InputFilter;
import swtor.parser.filter.OutputFilter;

public interface Parser {

	public void parse() throws IOException, LogParserException;

	public void addInputFilter(InputFilter filter);

	public void removeInputFilter(InputFilter filter);

	public void clearInputFilters();

	public void addOutputFilter(OutputFilter filter);

	public void removeOutputFilter(OutputFilter filter);

	public void clearOutputFilters();

}
