package custom;

import org.zkoss.zk.ui.event.Event;

import jease.cmf.service.Nodes;
import jease.cmf.web.node.browser.NodeBrowserWindow;
import jease.cms.web.content.editor.ContentEditor;
import jease.cms.web.component.Linkfield;
import jfix.util.I18N;
import jfix.zk.ActionListener;
import jfix.zk.Images;
import jfix.zk.Button;
import jfix.zk.Intfield;
import jfix.zk.Linkbutton;
import jfix.zk.Mediafield;
import jfix.zk.RichTextarea;
import jfix.zk.Row;
import jfix.zk.Textarea;
import jfix.zk.Textfield;
import jfix.zk.WebBrowser;

public class MasterThesisEditor extends 
                    ContentEditor<MasterThesis> {

 Textfield author = new Textfield();
 Mediafield authorPhoto = new Mediafield();
 Textfield authorUrl = new Textfield();
 Textarea bio = new Textarea();
 Textarea description = new Textarea();
 Linkfield smallphoto = new Linkfield(); 
 Linkfield bigphoto = new Linkfield(); 
 RichTextarea topic = new RichTextarea();
 Textfield director = new Textfield();
 Intfield qualification = new Intfield();
 Textfield url = new Textfield();
 Textfield downloadUrl = new Textfield();
 
 public MasterThesisEditor() {
 }

 public void init() {
	 add("Author", author);
	 add("Author Photo", authorPhoto);
	 add("Author URL", authorUrl);
	 add("Bio", bio, "Please enter bio.");
	 add("Description", description, "Please enter topic.");
	 add("Small Photo", smallphoto);
	 add("Big Photo", bigphoto);
	 add("Topic", topic, "Please enter topic.");
	 add("Director", director);
	 add("Qualification", qualification);
	 add("URL", url);
	 add("Download URL", downloadUrl);
 }

 public void load() {
	 author.setText(getNode().getAuthor());
	 authorPhoto.setMedia("authorPhoto.jpg", "image/jpg", getNode().getAuthorPhoto());
	 authorUrl.setText(getNode().getAuthorUrl());
	 bio.setText(getNode().getBio());
	 description.setText(getNode().getDescription());
	 smallphoto.setValue(getNode().getSmallPhoto());
	 bigphoto.setValue(getNode().getBigPhoto());
	 topic.setText(getNode().getTopic());
	 director.setText(getNode().getDirector());
	 qualification.setValue(getNode().getQualification());
	 url.setText(getNode().getUrl());
	 downloadUrl.setText(getNode().getDownloadUrl());
  }

 public void validate() {
  validate(topic.isEmpty(), "Topic required");
  validate(author.isEmpty(), "Author required");
  validate(director.isEmpty(), "Director required");
  //TODO validate(!"image/jpeg".equals(authorPhoto.getContentType()), "Author Photo not valid. Should be jpeg.");
  //TODO validate URLs !!
 }

 public void save() {
  getNode().setAuthor(author.getText());
  authorPhoto.copyToFile(getNode().getAuthorPhoto());
  getNode().setAuthorUrl(authorUrl.getText());
  getNode().setBio(bio.getText());
  getNode().setDescription(description.getText());
  getNode().setSmallPhoto(smallphoto.getValue());
  getNode().setBigPhoto(bigphoto.getValue());
  getNode().setTopic(topic.getText());
  getNode().setDirector(director.getText());
  getNode().setQualification(qualification.getValue());
  getNode().setUrl(url.getText());
  getNode().setDownloadUrl(downloadUrl.getText());
 }
}
