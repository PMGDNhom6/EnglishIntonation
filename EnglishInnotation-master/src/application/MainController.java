package application;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

import javafx.animation.Animation.Status;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Cell;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Favorite;
import model.Subtitle;
import model.Video;
import service.SoundRecorderService;

public class MainController {

	@FXML
	private MediaView mediaVideo;
	@FXML
	private HBox mediaTool;
	@FXML
	private Label lblTitle;
	@FXML
	private TextField txtSearchSubtitle;
	@FXML
	private TextField txtSearchFavorite;
	@FXML
	private TextField txtSearchVideo;
	@FXML
	private CheckBox chkFavorite;
	@FXML
	private ListView<Subtitle> listSubtitle;
	@FXML
	private Button btnAddVideo;
	@FXML
	private Button btnDeleteVideo;
	@FXML
	private Button btnAddSubtitle;
	@FXML
	private ListView<Video> listVideo;
	@FXML
	private Button btnPlay;
	@FXML
	private Slider sliderTimeVideo;
	@FXML
	private Slider sliderVolume;
	@FXML
	private Label lblTimeVideo;
	@FXML
	private Slider sliderVloume;
	@FXML
	private TabPane tab;
	@FXML
	private Button btnRecord;
	@FXML
	private Button btnListenRecording;
	@FXML
	private ListView<Favorite> listFavorite;
	@FXML
	private Button btnRefreshListSubtitle;
	@FXML
	private Button btnRefreshListFavorite;
	@FXML
	private Button btnRefreshListVideo;
	@FXML
	private HBox hBoxMidia;
	@FXML
	private Pane paneImagePlay;
	@FXML
	private ImageView imvPlay;
	@FXML
	private Label lblVolume;
	@FXML
	private Pane paneLeftSection;

	// Unity
	// private Media media;
	private MediaPlayer mediaPlayer;
	private Duration duration;
	private List<Video> videos = new ArrayList<Video>();
	private List<Favorite> favorites = new ArrayList<Favorite>();
	private int indexSelectedVideo;
	private Alert alert;
	private Duration durFavorite = null;
	private final String favoriteFilePath = ".\\favorite.txt";
	private final String videoFilePath = ".\\videos.txt";
	private final String favoriteAction = "favorite";
	private final String videoAction = "video";
	private double volume = -1;
	private ArrayList<Integer> listSecond = new ArrayList<>();

	public void initialize() {
		System.out.println("initilize");

		tab.getSelectionModel().select(0);
		btnAddSubtitle.setDisable(true);
		btnDeleteVideo.setDisable(true);
		indexSelectedVideo = -1;
		mediaTool.setVisible(false);
		paneImagePlay.setVisible(false);
		hBoxMidia.setAlignment(Pos.CENTER);

		setImageButton();
		setListVideo();
		setListFavorite();

		setActionListVideo();
		setActionSliderTimeVideo();
		setActionSliderVolumn();
		setActionListSubtitle();
		setActionListFavorite();
		setActionSearchSubtitle();
		setActionSearchFavorite();
		setActionSearchVideo();
		setActionRefreshButton();
		setActionHBox();
		setActionImageView();
	}

	private void setActionSearchVideo() {
		// TODO Auto-generated method stub
		txtSearchVideo.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				for (int i = 0; i < listVideo.getItems().size(); i++) {
					if (listVideo.getItems().get(i).getName().toLowerCase().contains(newValue.toLowerCase())) {
						int index = i;
						int scrollValue = index > 0 ? index - 1 : index;
						listVideo.scrollTo(scrollValue);
						break;
					}
				}
			}
		});
	}

	private void setActionSliderVolumn() {
		sliderVolume.valueProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				// TODO Auto-generated method stub
				if (mediaPlayer != null) {
					volume = sliderVolume.getValue() / 100;
					mediaPlayer.setVolume(volume);
					if (sliderVolume.getValue() == 0) {
						lblVolume.setText("X");
						lblVolume.setLayoutY(10.4);
					} else if (sliderVolume.getValue() > 50) {
						lblVolume.setText("))");
						lblVolume.setLayoutY(9.4);
					} else {
						lblVolume.setText(")");
						lblVolume.setLayoutY(9.4);
					}
				}
			}
		});
		// TODO Auto-generated method stub
		sliderVolume.hoverProperty().addListener((obs, wasHovered, isNowHovered) -> {
			if (isNowHovered && mediaPlayer != null) {
				sliderVolume.setStyle("-fx-border-color:white;");
			} else {
				sliderVolume.setStyle("-fx-border-color:none;");
			}
		});
	}

	private void setActionImageView() {
		// TODO Auto-generated method stub
		imvPlay.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
			@Override
			public void handle(javafx.scene.input.MouseEvent event) {
				// TODO Auto-generated method stub
				if (mediaPlayer != null) {
					mediaPlayer.play();
					paneImagePlay.setVisible(false);
				}
			}
		});
	}

	private void setActionHBox() {
		// TODO Auto-generated method stub
		mediaTool.hoverProperty().addListener((obs, wasHovered, isNowHovered) -> {
			if (isNowHovered && mediaPlayer != null) {
				mediaTool.setVisible(true);
			} else {
				mediaTool.setVisible(false);
			}
		});

		hBoxMidia.hoverProperty().addListener((obs, wasHovered, isNowHovered) -> {
			if (isNowHovered && mediaPlayer != null) {
				mediaTool.setVisible(true);
			} else {
				mediaTool.setVisible(false);
			}
		});

		hBoxMidia.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
			@Override
			public void handle(javafx.scene.input.MouseEvent event) {
				// TODO Auto-generated method stub
				if (mediaPlayer != null) {
					if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
						mediaPlayer.pause();
						paneImagePlay.setVisible(true);
					} else {
						mediaPlayer.play();
						paneImagePlay.setVisible(false);
					}
				}
			}

		});
	}

	private void setImageButton() {
		// TODO Auto-generated method stub
		Image img;
		try {
			img = new Image(getClass().getResource("/images/btnAdd.png").toURI().toString(), 16, 16, true, true);
			btnAddVideo.setGraphic(new ImageView(img));
			img = new Image(getClass().getResource("/images/btnRemove.png").toURI().toString(), 16, 16, true, true);
			btnDeleteVideo.setGraphic(new ImageView(img));
			img = new Image(getClass().getResource("/images/btnRedRecord.png").toURI().toString(), 20, 20, true, true);
			btnRecord.setGraphic(new ImageView(img));
			img = new Image(getClass().getResource("/images/btnSpeaker.png").toURI().toString(), 20, 20, true, true);
			btnListenRecording.setGraphic(new ImageView(img));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// btnRemove.setStyle("-fx-background-color:transparent;");
	}

	private void setActionRefreshButton() {
		// TODO Auto-generated method stub
		btnRefreshListSubtitle.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				txtSearchSubtitle.setText("");
			}
		});

		btnRefreshListFavorite.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				txtSearchFavorite.setText("");
			}
		});

		btnRefreshListVideo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				txtSearchVideo.setText("");
			}
		});
	}

	private void setActionSearchFavorite() {
		// TODO Auto-generated method stub
		txtSearchFavorite.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				readListObject(favoriteFilePath, favoriteAction);
				List<Favorite> listFav = new ArrayList<>();
				listFav.addAll(listFavorite.getItems());
				listFavorite.getItems().clear();
				for (int i = 0; i < listFav.size(); i++) {
					if (listFav.get(i).getSubReference().getContent().toLowerCase().contains(newValue.toLowerCase())) {
						listFavorite.getItems().add(listFav.get(i));
					}
				}
			}
		});
	}

	private void setActionSearchSubtitle() {
		// TODO Auto-generated method stub
		txtSearchSubtitle.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if (indexSelectedVideo > -1) {
					readFileSubtitle(videos.get(indexSelectedVideo).getUrlSubtitle());
					List<Subtitle> listSub = new ArrayList<>();
					listSub.addAll(listSubtitle.getItems());
					listSubtitle.getItems().clear();
					for (int i = 0; i < listSub.size(); i++) {
						if (listSub.get(i).getContent().toLowerCase().contains(newValue.toLowerCase())) {
							listSubtitle.getItems().add(listSub.get(i));
						}
					}
				}
			}
		});
	}

	public void setActionListFavorite() {
		// TODO Auto-generated method stub
		/*
		 * listFavorite.getSelectionModel().selectedItemProperty().addListener(
		 * new ChangeListener<Favorite>() {
		 * 
		 * @Override public void changed(ObservableValue<? extends Favorite>
		 * observable, Favorite oldValue, Favorite newValue) { // TODO
		 * Auto-generated method stub if (newValue != null) { String vidRefName
		 * = newValue.getVidReference().getName(); for (int i = 0; i <
		 * listVideo.getItems().size(); i++) { if
		 * (vidRefName.equals(listVideo.getItems().get(i).toString()) &&
		 * indexSelectedVideo != i) { listVideo.getSelectionModel().select(i);
		 * break; } }
		 * 
		 * Duration durSliderTime = setSeekVideoAndValueSlider(null, newValue);
		 * double dbSliderTime = durSliderTime.toMillis();
		 * scrollToIndexListSubtitle(dbSliderTime);
		 * lblTimeVideo.setText(formatTime(durSliderTime, duration)); } }
		 * 
		 * });
		 */
		listFavorite.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
			@Override
			public void handle(javafx.scene.input.MouseEvent event) {
				// TODO Auto-generated method stub
				Favorite fav = listFavorite.getSelectionModel().getSelectedItem();
				if (fav != null) {
					String vidRefName = fav.getVidReference().getName();
					for (int i = 0; i < listVideo.getItems().size(); i++) {
						String itemName = listVideo.getItems().get(i).getName().toString();
						if (itemName.equals(vidRefName)) {
							listVideo.getSelectionModel().select(i);
							break;
						}
					}
					Duration durSliderTime = setSeekVideoAndValueSlider(null,
							listFavorite.getSelectionModel().getSelectedItem());
					double dbSliderTime = durSliderTime.toMillis();
					scrollToIndexListSubtitle(dbSliderTime);
					lblTimeVideo.setText(formatTime(durSliderTime, duration));
				}
			}
		});
	}

	private void setListFavorite() {
		// TODO Auto-generated method stub
		readListObject(favoriteFilePath, favoriteAction);
		listFavorite.setCellFactory(e -> {
			ListCell<Favorite> cell = new FavoriteCell();
			cell.hoverProperty().addListener((obs, wasHovered, isNowHovered) -> {
				HBox hBox = (HBox) cell.getGraphic();
				Button btnRemove = (Button) hBox.getChildren().get(2);
				if (isNowHovered && !cell.isEmpty()) {
					btnRemove.setVisible(true);
				} else {
					btnRemove.setVisible(false);
				}
			});
			return cell;
		});
	}

	public void setListVideo() {
		readListObject(videoFilePath, videoAction);
		listVideo.setCellFactory(e -> {
			ListCell<Video> cell = new VideoCell();
			cell.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
				HBox hBox = (HBox) cell.getGraphic();
				Button btnAddSub = (Button) hBox.getChildren().get(3);
				if (isSelected && !cell.isEmpty()) {
					btnAddSub.setVisible(true);
				} else {
					btnAddSub.setVisible(false);
				}
			});
			return cell;
		});
	}

	public void setActionListVideo() {
		listVideo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Video>() {

			@Override
			public void changed(ObservableValue<? extends Video> observable, Video oldValue, Video newValue) {
				// TODO Auto-generated method stub
				if (newValue == null) {
					// reset state
					btnPlay.setText("►");
					btnPlay.setStyle("-fx-font: 12 arial;-fx-background-radius:15px;-fx-background-color:white;");
					sliderTimeVideo.setValue(0);
					listSubtitle.getItems().clear();

					lblTitle.setText("");

					btnDeleteVideo.setDisable(true);
					btnAddSubtitle.setDisable(true);
					return;
				}
				paneImagePlay.setVisible(false);
				btnDeleteVideo.setDisable(false);
				indexSelectedVideo = listVideo.getSelectionModel().getSelectedIndex();

				lblTitle.setText(videos.get(indexSelectedVideo).getName());
				durFavorite = null;
				setMediaVideo(videos.get(indexSelectedVideo).getUrlVideo());

				if (videos.get(indexSelectedVideo).getUrlSubtitle() != null) {
					readFileSubtitle(videos.get(indexSelectedVideo).getUrlSubtitle());
					listSubtitle.scrollTo(0);
					listSubtitle.setCellFactory(e -> {
						ListCell<Subtitle> cell = new SubTitleCell();
						cell.hoverProperty().addListener((obs, wasHovered, isNowHovered) -> {
							HBox hBox = (HBox) cell.getGraphic();
							Button btnAdd = (Button) hBox.getChildren().get(2);
							if (isNowHovered && !cell.isEmpty()) {
								btnAdd.setVisible(true);
							}
							if (isNowHovered && !cell.isSelected()) {
								cell.setStyle("-fx-background-color:#f6f6f6;");
							}
							if (wasHovered) {
								btnAdd.setVisible(false);
							}
							if (wasHovered && !cell.isSelected()) {
								cell.setStyle("-fx-background-color:#fff;");
							}
						});

						cell.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
							cell.setStyle("-fx-background-color:#fff;");
							if (isNowFocused && !cell.isEmpty()) {
								cell.setStyle("-fx-background-color:#d3d3d3;");
							}
						});

						return cell;
					});
				}
				btnAddSubtitle.setDisable(false);
			}
		});
	}

	public void setActionListSubtitle() {
		listSubtitle.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Subtitle>() {
			@Override
			public void changed(ObservableValue<? extends Subtitle> observable, Subtitle oldValue, Subtitle newValue) {
				// TODO Auto-generated method stub
				if (newValue != null) {
					Duration durSliderTime = setSeekVideoAndValueSlider(newValue, null);
					lblTimeVideo.setText(formatTime(durSliderTime, duration));
				}
			}
		});
	}

	Duration setSeekVideoAndValueSlider(Subtitle sub, Favorite fav) {
		SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

		Date date = null;
		try {
			if (sub != null) {
				date = sdf.parse(sub.getTime());
			} else {
				date = sdf.parse(fav.getSubReference().getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Duration durSlderTime = Duration.millis(date.getTime());
		durFavorite = durSlderTime;
		mediaPlayer.seek(durSlderTime);
		duration = mediaPlayer.getMedia().getDuration();
		double dbSliderTime = durSlderTime.toMillis();
		double totalTime = duration.toMillis();
		sliderTimeVideo.setValue((100 / totalTime) * dbSliderTime);
		return durSlderTime;
	}

	public void recordVoiceListener(ActionEvent e) throws InterruptedException {
		if (mediaPlayer != null) {
			mediaPlayer.pause();
		}
		// show dialog
		alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Record Audio");
		alert.setHeaderText("");
		alert.getDialogPane().setPrefSize(10, 10);
		alert.setY(590);
		alert.setX(751);
		alert.getDialogPane().lookupButton(ButtonType.OK).setVisible(false);
		alert.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
		HBox hBox = new HBox();
		// vBox.setPrefSize(100, 100);
		hBox.setAlignment(Pos.CENTER);
		Image img;
		try {
			img = new Image(this.getClass().getResource("/images/btnRedRecordDot.png").toURI().toString(), 20, 20, true,
					true);
			hBox.getChildren().addAll(new ImageView(img), new Label(" Recording......"));
			alert.setGraphic(hBox);
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/images/btnRecord.png").toString()));
		alert.show();
		// creates a new thread that waits for a specified
		// of time before stopping
		SoundRecorderService recorder = new SoundRecorderService();
		// recording
		recorder.start();
		Thread stopper = new Thread(new Runnable() {
			public void run() {
				recorder.captureAudio();
			}
		});
		// start
		stopper.start();
		Thread.sleep(recorder.RECORD_TIME);
		recorder.finish();
		alert.hide();
		alert.close();
	}

	public void playAudioRecordedListener(ActionEvent e) {
		try {
			if (mediaPlayer != null) {
				mediaPlayer.pause();
			}
			SoundRecorderService rds = new SoundRecorderService();
			File file = new File(rds.filePath);
			if (file.exists()) {
				Clip clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(file));
				clip.start();
			}
			/*
			 * FloatControl gainControl = (FloatControl)
			 * clip.getControl(FloatControl.Type.MASTER_GAIN); double gain = 0;
			 * // number between 0 and 1 (loudest) float dB = (float)
			 * (Math.log(gain) / Math.log(10.0) * 20.0);
			 * gainControl.setValue(dB);
			 */

		} catch (MalformedURLException murle) {
			murle.printStackTrace();
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void handleButtonPlay(ActionEvent e) {
		MediaPlayer.Status status = mediaPlayer.getStatus();

		if (status == MediaPlayer.Status.UNKNOWN || status == MediaPlayer.Status.HALTED) {
			// don't do anything in these states
			return;
		}

		if (status == MediaPlayer.Status.PAUSED || status == MediaPlayer.Status.READY
				|| status == MediaPlayer.Status.STOPPED) {
			mediaPlayer.play();
			btnPlay.setText("❙❙");
			btnPlay.setPrefHeight(30);
			btnPlay.setPrefWidth(30);
			btnPlay.setStyle("-fx-font: 9 arial;-fx-background-radius:15px;-fx-background-color:white;");
		} else {
			mediaPlayer.pause();
			btnPlay.setText("►");
			btnPlay.setStyle("-fx-font: 12 arial;-fx-background-radius:15px;-fx-background-color:white;");
		}
	}

	public void setMediaVideo(String uri) {
		if (uri == null)
			return;
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer = null;
		}

		Media media = new Media(uri);
		mediaPlayer = new MediaPlayer(media);
		mediaVideo.setMediaPlayer(mediaPlayer);
		mediaVideo.setPreserveRatio(true);
		// reset state
		btnPlay.setText("►");
		btnPlay.setStyle("-fx-font: 12 arial;-fx-background-radius:15px;-fx-background-color:white;");
		sliderTimeVideo.setValue(0);
		listSubtitle.getItems().clear();
		// action
		play();
	}

	public void setActionSliderTimeVideo() {
		sliderTimeVideo.valueProperty().addListener(new InvalidationListener() {
			public void invalidated(Observable ov) {
				if (mediaPlayer != null && sliderTimeVideo.isValueChanging()) {
					// multiply duration by percentage calculated by slider
					// position
					double sliderTime = sliderTimeVideo.getValue();
					Duration durSlderTime = duration.multiply(sliderTime / 100.0);
					mediaPlayer.seek(durSlderTime);
					double dbSliderTime = durSlderTime.toMillis();
					scrollToIndexListSubtitle(dbSliderTime);
				}
			}
		});

		sliderTimeVideo.hoverProperty().addListener((obs, wasHovered, isNowHovered) -> {
			if (isNowHovered && mediaPlayer != null) {
				sliderTimeVideo.setStyle("-fx-border-color:white;");
			} else {
				sliderTimeVideo.setStyle("-fx-border-color:none;");
			}
		});
	}

	void scrollToIndexListSubtitle(double dbSliderTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date = null;
		for (int i = 0; i < listSubtitle.getItems().size(); i++) {
			date = new Date();
			try {
				date = sdf.parse(listSubtitle.getItems().get(i).getTime());
				double itemTime = Duration.millis(date.getTime()).toMillis();
				if (itemTime > dbSliderTime) {
					int index = i - 1;
					int scrollValue = index > 5 ? index - 6 : 0;
					listSubtitle.scrollTo(scrollValue);
					listSubtitle.getSelectionModel().select(index);
					break;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	int count = 1;
	int tempSecond;

	public void updateTimeVideo() {
		Platform.runLater(new Runnable() {
			@SuppressWarnings("deprecation")
			public void run() {
				Duration currentTime = mediaPlayer.getCurrentTime();
				lblTimeVideo.setText(formatTime(currentTime, duration));
				sliderTimeVideo.setDisable(duration.isUnknown());
				if (!sliderTimeVideo.isDisabled() && duration.greaterThan(Duration.ZERO)
						&& !sliderTimeVideo.isValueChanging()) {
					double dbSliderTime = currentTime.divide(duration).toMillis() * 100.0;
					// System.out.println(currentTime.divide(duration).toMillis());
					sliderTimeVideo.setValue(dbSliderTime);
				}

				double dbCurrenTime = currentTime.toSeconds();
				int intCurrentTime = (int) dbCurrenTime;
				if (listSecond.contains(intCurrentTime) && count == 1) {
					tempSecond = intCurrentTime;
					count++;
					for (int i = 0; i < listSubtitle.getItems().size(); i++) {
						if (listSubtitle.getItems().get(i).getSecond() == intCurrentTime) {
							int index = i;
							int scrollValue = index > 5 ? index - 6 : 0;
							listSubtitle.scrollTo(scrollValue);
							listSubtitle.getFocusModel().focus(index);
						}
					}
				}
				if (intCurrentTime > tempSecond) {
					count = 1;
				}
			}
		});
	}

	public String formatTime(Duration elapsed, Duration duration) {
		int intElapsed = (int) Math.floor(elapsed.toSeconds());
		int elapsedHours = intElapsed / (60 * 60);
		if (elapsedHours > 0) {
			intElapsed -= elapsedHours * 60 * 60;
		}
		int elapsedMinutes = intElapsed / 60;
		int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;

		if (duration.greaterThan(Duration.ZERO)) {
			int intDuration = (int) Math.floor(duration.toSeconds());
			int durationHours = intDuration / (60 * 60);
			if (durationHours > 0) {
				intDuration -= durationHours * 60 * 60;
			}
			int durationMinutes = intDuration / 60;
			int durationSeconds = intDuration - durationHours * 60 * 60 - durationMinutes * 60;
			if (durationHours > 0) {
				return String.format("%d:%02d:%02d/%d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds,
						durationHours, durationMinutes, durationSeconds);
			} else {
				return String.format("%02d:%02d/%02d:%02d", elapsedMinutes, elapsedSeconds, durationMinutes,
						durationSeconds);
			}
		} else {
			if (elapsedHours > 0) {
				return String.format("%d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds);
			} else {
				return String.format("%02d:%02d", elapsedMinutes, elapsedSeconds);
			}
		}
	}

	public void play() {
		mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
			public void invalidated(Observable ov) {
				updateTimeVideo();
			}
		});
		mediaPlayer.setOnPlaying(new Runnable() {
			public void run() {
				btnPlay.setPrefHeight(30);
				btnPlay.setPrefWidth(30);
				paneImagePlay.setVisible(false);
				btnPlay.setText("❙❙");
				btnPlay.setStyle("-fx-font: 9 arial;-fx-background-radius:15px;-fx-background-color:white;");
			}
		});

		mediaPlayer.setOnPaused(new Runnable() {
			public void run() {
				btnPlay.setText("►");
				btnPlay.setStyle("-fx-font: 12 arial;-fx-background-radius:15px;-fx-background-color:white;");
				paneImagePlay.setVisible(true);
			}
		});
		mediaPlayer.setOnReady(new Runnable() {
			public void run() {
				duration = mediaPlayer.getMedia().getDuration();
				if (volume > -1) {
					sliderVolume.setValue(volume * 100);
					mediaPlayer.setVolume(volume);
				} else {
					sliderVolume.setValue(mediaPlayer.getVolume() * 100);
				}
				updateTimeVideo();
				if (durFavorite != null) {
					mediaPlayer.seek(durFavorite);
				}
			}
		});
		mediaPlayer.setOnEndOfMedia(new Runnable() {
			public void run() {
				mediaPlayer.seek(mediaPlayer.getStartTime());
				sliderTimeVideo.setValue(0);
				mediaPlayer.pause();
			}
		});
	}

	public void readFileSubtitle(String url) {
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(url)))) {
			String line;
			listSubtitle.getItems().clear();
			while ((line = reader.readLine()) != null) {
				// System.out.println(line);
				String[] arrWords = line.split("\t");
				String time = arrWords[0];
				String content = arrWords[1];
				int second = formatTimeStringToSecondInteger(time);
				Subtitle sub = new Subtitle(time, content, second);
				listSubtitle.getItems().add(sub);
				listSecond.add(second);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	int formatTimeStringToSecondInteger(String time) {
		int timeInt = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Duration dur = Duration.millis(date.getTime());
		timeInt = (int) dur.toSeconds();
		return timeInt;
	}

	public File locateFile(Node node) {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Chon Video");
		return chooser.showOpenDialog(node.getScene().getWindow());

	}

	public void addVideo(ActionEvent event) {
		Node node = (Node) event.getSource();
		File file = locateFile(node);
		if (file != null) {
			Video vid = new Video(file.getName(), file.toURI().toString());
			videos.add(vid);
			listVideo.getItems().add(vid);
			writeListObject(videoFilePath, videos, null);
		}

	}

	public void addSubtitle(ActionEvent event) {
		if (indexSelectedVideo > -1) {
			Node node = (Node) event.getSource();
			File file = locateFile(node);
			if (file != null) {
				listSubtitle.getItems().clear();
				readFileSubtitle(file.toString());
				videos.get(indexSelectedVideo).setUrlSubtitle(file.toString());
			}
		}

	}

	public void deleteVideo(ActionEvent event) {
		if (indexSelectedVideo > -1) {
			videos.remove(indexSelectedVideo);
			listVideo.getItems().remove(indexSelectedVideo);
		}
	}

	public void writeListObject(String fileName, List<Video> listVid, List<Favorite> listFav) {
		try {
			File file = new File(fileName);
			if (file.exists()) {
				file.delete();
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			FileOutputStream fout = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			if (listVid != null) {
				oos.writeObject(listVid);
			} else {
				oos.writeObject(listFav);
			}

			oos.close();
			fout.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found Write Video");
		} catch (IOException e) {
			System.out.println("Error initializing stream Write Video");
		}
	}

	@SuppressWarnings("unchecked")
	public void readListObject(String fileName, String actionName) {
		try {
			FileInputStream fin = new FileInputStream(new File(fileName));
			ObjectInputStream ois = new ObjectInputStream(fin);

			if (actionName == videoAction) {
				videos = (ArrayList<Video>) ois.readObject();
				listVideo.getItems().clear();
				for (int i = 0; i < videos.size(); i++) {
					listVideo.getItems().add(videos.get(i));
				}
			} else {
				favorites = (ArrayList<Favorite>) ois.readObject();
				listFavorite.getItems().clear();
				for (int i = 0; i < favorites.size(); i++) {
					// System.out.println(videos.get(i).getName());
					listFavorite.getItems().add(favorites.get(i));
				}
			}

			ois.close();
			fin.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found Read Video");
		} catch (IOException e) {
			System.out.println("Error initializing stream Read Video");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void stop() {
		writeListObject(videoFilePath, videos, null);
		writeListObject(favoriteFilePath, null, favorites);
		SoundRecorderService recorder = new SoundRecorderService();
		File wavFile = new File(recorder.filePath);
		try {
			Files.deleteIfExists(wavFile.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("stop");
	}

	class SubTitleCell extends ListCell<Subtitle> {
		HBox hBox = new HBox();
		Button btnAdd = new Button();
		Pane pane = new Pane();
		Label lbText = new Label("");

		public SubTitleCell() {
			super();
			btnAdd.setVisible(false);
			try {
				Image img = new Image(getClass().getResource("/images/btnAdd.png").toURI().toString(), 16, 16, true,
						true);
				btnAdd.setGraphic(new ImageView(img));
				btnAdd.setStyle("-fx-background-color:transparent;");
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			lbText.getStyleClass().add("label-black");
			hBox.getChildren().addAll(lbText, pane, btnAdd);
			hBox.setHgrow(pane, Priority.ALWAYS);
			btnAdd.setOnAction(e -> {
				Subtitle sub = getItem();
				boolean exist = false;
				for (int i = 0; i < favorites.size(); i++) {
					Subtitle subRef = favorites.get(i).getSubReference();
					Video vidRef = favorites.get(i).getVidReference();
					if (sub.getTime().equals(subRef.getTime()) && sub.getContent().equals(subRef.getContent())
							&& videos.get(indexSelectedVideo).getName().equals(vidRef.getName())) {
						exist = true;
						break;
					}
				}
				if (exist) {
					// show dialog
					String title = "Message";
					String headerText = "This sentence already exists!";
					this.showDialog(AlertType.INFORMATION, title, headerText);
				} else {
					Video vid = videos.get(indexSelectedVideo);
					Favorite fav = new Favorite(vid, sub);
					listFavorite.getItems().add(fav);
					favorites.add(fav);
					// show dialog
					String title = "Message";
					String headerText = "Save successfully!";
					this.showDialog(AlertType.INFORMATION, title, headerText);
					// writeListObject(favoriteFilePath, null, favorites);
				}
			});
		}

		void showDialog(AlertType type, String title, String headerText) {
			// TODO Auto-generated method stub
			alert = new Alert(type);
			alert.setTitle(title);
			alert.setHeaderText(headerText);
			alert.show();
		}

		@Override
		protected void updateItem(Subtitle item, boolean empty) {
			// TODO Auto-generated method stub
			super.updateItem(item, empty);
			setText(null);
			setGraphic(null);
			if (!empty) {
				lbText.setText(item.getTime() + "\t" + item.getContent());
				setGraphic(hBox);
			}
		}

	}

	class FavoriteCell extends ListCell<Favorite> {
		HBox hBox = new HBox();
		Button btnRemove = new Button();
		Pane pane = new Pane();
		Label lbText = new Label("");

		public FavoriteCell() {
			super();
			btnRemove.setVisible(false);
			try {
				Image img = new Image(getClass().getResource("/images/btnRemove.png").toURI().toString(), 16, 16, true,
						true);
				btnRemove.setGraphic(new ImageView(img));
				btnRemove.setStyle("-fx-background-color:transparent;");
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			lbText.getStyleClass().add("label-black");
			hBox.getChildren().addAll(lbText, pane, btnRemove);
			hBox.setHgrow(pane, Priority.ALWAYS);
			btnRemove.setOnAction(e -> {
				Favorite fav = getItem();
				favorites.remove(getItem());
				listFavorite.getItems().remove(getItem());
			});
		}

		@Override
		protected void updateItem(Favorite item, boolean empty) {
			// TODO Auto-generated method stub
			super.updateItem(item, empty);
			setText(null);
			setGraphic(null);
			if (!empty) {
				String videoName = "[" + item.getVidReference().getName() + "]";
				String time = item.getSubReference().getTime();
				String content = item.getSubReference().getContent();
				lbText.setText(videoName + "\t" + time + "\t" + content);
				setGraphic(hBox);
			}
		}

	}

	class VideoCell extends ListCell<Video> {
		HBox hBox = new HBox();
		Button btnAddSub = new Button("Add Sub");
		Pane pane = new Pane();
		Label lbText = new Label("");
		ImageView imgView = new ImageView();

		public VideoCell() {
			super();
			btnAddSub.setVisible(false);
			btnAddSub.setPrefHeight(16);
			btnAddSub.setPrefWidth(80);
			btnAddSub.setStyle("-fx-font: 11 arial;");
			Image img;
			try {
				img = new Image(getClass().getResource("/images/btnAddSub.png").toURI().toString(), 16, 16, true, true);
				btnAddSub.setGraphic(new ImageView(img));
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			lbText.getStyleClass().add("label-black");
			hBox.getChildren().addAll(imgView, lbText, pane, btnAddSub);
			hBox.setHgrow(pane, Priority.ALWAYS);
			btnAddSub.setOnAction(e -> {
				if (indexSelectedVideo > -1) {
					Node node = (Node) e.getSource();
					File file = locateFile(node);
					if (file != null) {
						listSubtitle.getItems().clear();
						readFileSubtitle(file.toString());
						videos.get(indexSelectedVideo).setUrlSubtitle(file.toString());
					}
				}
			});
		}

		@Override
		protected void updateItem(Video item, boolean empty) {
			// TODO Auto-generated method stub
			super.updateItem(item, empty);
			setText(null);
			setGraphic(null);
			if (!empty) {
				lbText.setText("  " + item.getName());
				Image img;
				try {
					img = new Image(getClass().getResource("/images/btnYoutube.png").toURI().toString(), 16, 16, true,
							true);
					imgView.setImage(img);
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				setGraphic(hBox);
			}
		}

	}
}
