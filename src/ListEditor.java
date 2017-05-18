/* 2017年春学期 Javaアプリケーション開発 授業中の演習 */

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ListView.EditEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ListEditor extends Application {
	private TextField input;
	private ListView<String> list;
	private Button open, save;
	ObservableList<String> model;

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		input = new TextField();
		list = new ListView<>();
		list.setEditable(true);
		list.setCellFactory(TextFieldListCell.forListView());
		open = new Button("開く...");
		save = new Button("保存...");

		//openボタンの処理
		open.setOnAction(
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						// TODO 自動生成されたメソッド・スタブ
						try {
							List<String> lines = Files.readAllLines(Paths.get("out.txt"));
							model.setAll(lines);
						} catch (IOException e) {
							// TODO 自動生成された catch ブロック
							e.printStackTrace();
						}
					}
				}
		);

		//saveボタンの処理
		save.setOnAction(
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						// TODO 自動生成されたメソッド・スタブ
						try {
							Files.write(Paths.get("out.txt"), model);
						} catch (IOException e) {
							// TODO 自動生成された catch ブロック
							e.printStackTrace();
						}
					}
				}
		);


		// 入力完了時のイベントハンドラ
		input.setOnAction(
			(ActionEvent e) -> {
				System.out.println("入力されたよ！");
				model.add(input.getText());
				input.setText("");

			}
		);
		// セル編集後のイベントハンドラ
		list.setOnEditCommit(
			(EditEvent<String> e) -> {
				System.out.println(	e.getIndex() + "番目の項目が編集されたよ！" + e.getNewValue());
				model.set(e.getIndex(), e.getNewValue());

			}
		);

		// ファイルの読み込みのテスト（読み込んだ各行をListViewに表示）
		model = list.getItems();
//		model.add("ダミー");
		List<String> lines = Files.readAllLines(Paths.get("out.txt"));
		model.setAll(lines);

		// ファイル書き出しのテスト
		Files.write(Paths.get("out.txt"), model);

		// 画面レイアウト
		root.setTop(input);
		root.setCenter(list);
		BorderPane bottomPane = new BorderPane();
		bottomPane.setLeft(open);
		bottomPane.setRight(save);
		root.setBottom(bottomPane);

		// ファイル選択ダイアログのテスト
		FileChooser fc = new FileChooser();
		File fR = fc.showOpenDialog(null);
		System.out.println(fR);

		Scene scene = new Scene(root, 300, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
