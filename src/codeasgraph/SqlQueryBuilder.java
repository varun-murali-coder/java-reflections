package codeasgraph;

import java.util.List;

import codeasgraph.Annotations.DependsOn;
import codeasgraph.Annotations.FinalResult;
import codeasgraph.Annotations.Input;
import codeasgraph.Annotations.Operation;

public class SqlQueryBuilder {
@Input("ids")
private List<String> ids;
@Input("limit")
private Integer limit;
@Input("columnNames")
private List<String> columnNames;
@Input("tableName")
private String tableName;
public SqlQueryBuilder(List<String> ids, Integer limit, List<String> columnNames, String tableName) {
	this.ids = ids;
	this.limit = limit;
	this.columnNames = columnNames;
	this.tableName = tableName;
}

@Operation("selecQueryBuilder")
private String selectQueryBuilder(@Input("tableName")
String tableName,@Input("columnNames")
List<String> columnNames) {
	
	String colNames=columnNames.isEmpty()?"*":String.join(",", columnNames);
	return String.format("select %s from %s", colNames,tableName);
	
}
@Operation("whereClauseBuilder")
private String addWhereClause(@DependsOn("selecQueryBuilder")String query,@Input("ids")
List<String> ids) {
	if(ids.isEmpty())
		return query;
	return String.format("%s in (%s)", query,String.join(",", ids));
}

@FinalResult
private String addLimit(@DependsOn("whereClauseBuilder")String query,@Input("limit")
Integer limit) {
	
	if(limit==null||limit==0)
		return query;
	if(limit<0)
		throw new RuntimeException("Negative values not allowed for limit");
	return String.format("%s limit %d", query,limit);
}


	
}
