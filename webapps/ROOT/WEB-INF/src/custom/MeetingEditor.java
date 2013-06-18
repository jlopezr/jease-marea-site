package custom;

import jease.cms.web.content.editor.ContentEditor;
import jfix.zk.Datetimefield;
import jfix.zk.RichTextarea;
import jfix.zk.Textfield;

public class MeetingEditor extends 
                    ContentEditor<Meeting> {

 RichTextarea topic = new RichTextarea();
 Textfield location = new Textfield();
 Datetimefield start = new Datetimefield();
 Datetimefield stop = new Datetimefield();

 public MeetingEditor() {
 }

 public void init() {
  add("Topic", topic, "Please enter topic.");
  add("Location", location);
  add("Start", start);
  add("Stop", stop);
 }

 public void load() {
  topic.setText(getNode().getTopic());
  location.setText(getNode().getLocation());
  start.setDate(getNode().getStart());
  stop.setDate(getNode().getStop());
 }

 public void validate() {
  validate(topic.isEmpty(), "Topic required");
  validate(location.isEmpty(), "Location required");
  validate(start.isEmpty() || stop.isEmpty(),
     "Date required");
  validate(start.getValue().after(stop.getValue()), 
      "Date invalid");
 }

 public void save() {
  getNode().setTopic(topic.getText());
  getNode().setLocation(location.getText());
  getNode().setStart(start.getDate());
  getNode().setStop(stop.getDate());
 }
}
