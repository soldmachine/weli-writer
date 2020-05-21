import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData(
        primaryColor: Colors.blue,
      ),
      home: RandomWords(),
    );
  }
}

class RandomWords extends StatefulWidget {
  @override
  RandomWordsState createState() => new RandomWordsState();
}

class RandomWordsState extends State<RandomWords> {
  int _roundCount = 1;

  @override
  void initState() {
    super.initState();
    loadData(1);
  }

  loadData(int newRoundCount) async {
    print("loadData called with $newRoundCount");
    if (mounted) {
      setState(() {
        this._roundCount = newRoundCount;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text('Weli Writer'),
        ),
        body: buildScrollView());
  }

  Widget buildScrollView() => SingleChildScrollView(
        child: createTable(),
      );

  Widget createTable() {
    List<TableRow> rows = [];
    rows.add(_buildTableHeader());
    for (int i = 0; i < _roundCount; ++i) {
      rows.add(_buildTableRow());
    }
    rows.add(_buildTableButtonRow());
    return Table(children: rows);
  }

  TableRow _buildTableHeader() {
    return TableRow(children: [
      _tableHeaderItem("Sold"),
      _tableHeaderItem("W"),
      _tableHeaderItem("Maikal"),
      _tableHeaderItem("Egi"),
    ]);
  }

  TableRow _buildTableRow() {
    return TableRow(children: [
      _tableRowItem(21),
      _tableRowItem(21),
      _tableRowItem(21),
      _tableRowItem(21),
    ]);
  }

  TableRow _buildTableButtonRow() {
    return TableRow(children: [
      _tableRowButtonItem("+"),
      _tableRowButtonItem("+"),
      _tableRowButtonItem("+"),
      _tableRowButtonItem("+"),
    ]);
  }

  Widget _tableHeaderItem(String name) {
    return Container(
      padding: EdgeInsets.all(16),
      child: Text(
        name,
        textAlign: TextAlign.center,
        style: TextStyle(fontWeight: FontWeight.bold),
      ),
      color: Colors.green[800],
    );
  }

  Container _tableRowItem(int score) {
    return Container(
      padding: EdgeInsets.all(16),
      color: Colors.green[400],
      child: Text(
        "$score",
        textAlign: TextAlign.center,
      ),
    );
  }

  Container _tableRowButtonItem(String text) {
    return Container(
      padding: EdgeInsets.all(16),
      color: Colors.green[400],
      child: FlatButton(
        child: Text(
          text,
          textAlign: TextAlign.center,
        ),
        onPressed: doSomething,
      ),
    );
  }

  doSomething() {
    loadData(++_roundCount);
  }
}
