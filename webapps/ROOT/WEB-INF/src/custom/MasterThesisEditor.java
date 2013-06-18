package custom;

import org.zkoss.zk.ui.event.Event;

import jease.cmf.service.Nodes;
import jease.cmf.web.node.browser.NodeBrowserWindow;
import jease.cms.web.content.editor.ContentEditor;
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
 //
 Textfield smallphoto_url = new Textfield();
 Linkbutton smallphoto_link = new Linkbutton();
 Button smallphoto_browse = new Button(I18N.get("Browser"), Images.UserHome);
 //
 Textfield bigphoto_url = new Textfield();
 Linkbutton bigphoto_link = new Linkbutton();
 Button bigphoto_browse = new Button(I18N.get("Browser"), Images.UserHome); 
 //
 RichTextarea topic = new RichTextarea();
 Textfield director = new Textfield();
 Intfield qualification = new Intfield();
 Textfield url = new Textfield();
 Textfield downloadUrl = new Textfield();
 
 public MasterThesisEditor() {
	 //
	 smallphoto_link.setTooltiptext(I18N.get("Open"));
	 smallphoto_link.setImage(Images.InternetWebBrowser);
	 smallphoto_link.setWidth("24px");
	 smallphoto_link.addClickListener(new ActionListener() {
             public void actionPerformed(Event event) {
                     if (!smallphoto_url.isEmpty()) {
                             getRoot().appendChild(new WebBrowser(smallphoto_url.getText()));
                     }
             }
     });
	 smallphoto_browse.addClickListener(new ActionListener() {
             public void actionPerformed(Event event) {
                     smallphoto_browsePerformed();
             }
     });
	 //
	 bigphoto_link.setTooltiptext(I18N.get("Open"));
	 bigphoto_link.setImage(Images.InternetWebBrowser);
	 bigphoto_link.setWidth("24px");
	 bigphoto_link.addClickListener(new ActionListener() {
             public void actionPerformed(Event event) {
                     if (!bigphoto_url.isEmpty()) {
                             getRoot().appendChild(new WebBrowser(bigphoto_url.getText()));
                     }
             }
     });
	 bigphoto_browse.addClickListener(new ActionListener() {
             public void actionPerformed(Event event) {
                     bigphoto_browsePerformed();
             }
     });
 }

 private void smallphoto_browsePerformed() {
     String path = smallphoto_url.getValue();
     if (path.startsWith("./~")) {
             path = path.substring(3);
     }
     final NodeBrowserWindow nodeBrowserWindow = new NodeBrowserWindow(
                     Nodes.getByPath(path));
     nodeBrowserWindow.setTitle(I18N.get("Browser"));
     nodeBrowserWindow.addCloseListener(new ActionListener() {
             public void actionPerformed(Event event) {
                     if (nodeBrowserWindow.getSelectedNode() != null) {
                             smallphoto_url.setText("./~"
                                             + nodeBrowserWindow.getSelectedNode().getPath());
                     }
             }
     });
     getRoot().appendChild(nodeBrowserWindow);
}

 private void bigphoto_browsePerformed() {
     String path = bigphoto_url.getValue();
     if (path.startsWith("./~")) {
             path = path.substring(3);
     }
     final NodeBrowserWindow nodeBrowserWindow = new NodeBrowserWindow(
                     Nodes.getByPath(path));
     nodeBrowserWindow.setTitle(I18N.get("Browser"));
     nodeBrowserWindow.addCloseListener(new ActionListener() {
             public void actionPerformed(Event event) {
                     if (nodeBrowserWindow.getSelectedNode() != null) {
                             bigphoto_url.setText("./~"
                                             + nodeBrowserWindow.getSelectedNode().getPath());
                     }
             }
     });
     getRoot().appendChild(nodeBrowserWindow);
}
 
 public void init() {
	 add("Author", author);
	 add("Author Photo", authorPhoto);
	 add("Author URL", authorUrl);
	 add("Bio", bio, "Please enter bio.");
	 add("Description", description, "Please enter topic.");
	 
	 add(I18N.get("Small Photo"), new Row(smallphoto_url, smallphoto_link));
     add("", smallphoto_browse);
	 
	 add(I18N.get("Big Photo"), new Row(bigphoto_url, bigphoto_link));
     add("", bigphoto_browse);
     
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
	 smallphoto_url.setText(getNode().getSmallPhoto());
	 bigphoto_url.setText(getNode().getBigPhoto());
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
  getNode().setSmallPhoto(smallphoto_url.getText());
  getNode().setBigPhoto(bigphoto_url.getText());
  getNode().setTopic(topic.getText());
  getNode().setDirector(director.getText());
  getNode().setQualification(qualification.getValue());
  getNode().setUrl(url.getText());
  getNode().setDownloadUrl(downloadUrl.getText());
 }
}
