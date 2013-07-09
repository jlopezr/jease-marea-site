package custom;

import jease.cmf.service.Nodes;
import jease.cmf.domain.Node;
import jease.cms.domain.Content;
import jfix.db4o.Blob;

public class MasterThesis extends Content {
    private String author;
    private String bio;
    private Blob authorPhoto = new Blob(); //EMBEDDED IMAGE
    private String authorUrl;  //URL
    private String description;
    private String topic;
    private String smallPhoto; //CONTENT
	private String bigPhoto;  //CONTENT
    private String director;
    private int qualification;
    private String url;  //URL
    private String downloadUrl; //URL

    public static String getTitleByPath(String path) {
    	if(path.startsWith("./~")) {
    		path = path.substring(3);
    	}
    	Node n = Nodes.getByPath(path);
    	
    	if(n==null && n instanceof Content) {
    		return null;
    	} else {
    		String title = ((Content)n).getTitle();
    		return title;
    	}
    }
     
    public String getSmallPhoto() {
		return smallPhoto;
	}

	public void setSmallPhoto(String smallPhoto) {
		this.smallPhoto = smallPhoto;
	}

    public String getBigPhoto() {
		return bigPhoto;
	}

	public void setBigPhoto(String bigPhoto) {
		this.bigPhoto = bigPhoto;
	}
    
    public String getAuthorUrl() {
		return authorUrl;
	}

	public void setAuthorUrl(String authorUrl) {
		this.authorUrl = authorUrl;
	}
    
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAuthor() {
		return author;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

    public java.io.File getAuthorPhoto() {
        return authorPhoto.getFile();
    }
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQualification() {
		return qualification;
	}

	public void setQualification(int qualification) {
		this.qualification = qualification;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public StringBuilder getFulltext() {
        return super.getFulltext().append("\n")
                      .append(topic).append("\n")
                      .append(author).append("\n")
                      .append(director).append("\n")
                      .append(bio).append("\n")
                      .append(description).append("\n");
    }

    public void replace(String target, String replacement) {
       super.replace(target, replacement);
       setTopic(getTopic().replace(target, replacement));
       setAuthor(getAuthor().replace(target, replacement));
       setBio(getBio().replace(target, replacement));
       setDescription(getDescription().replace(target, replacement));
       setDirector(getDirector().replace(target, replacement));
       
       //TODO Need to replace also in URL and Images?       
    }

    public MasterThesis copy(boolean recursive) {
        MasterThesis mt = (MasterThesis) super.copy(recursive);
        mt.setAuthor(getAuthor());
        mt.setAuthorUrl(getAuthorUrl());
        mt.setBio(getBio());
        mt.setDescription(getDescription());
        mt.setSmallPhoto(getSmallPhoto());
        mt.setBigPhoto(getBigPhoto());
        mt.setTopic(getTopic());
        mt.setDirector(getDirector());
        mt.setQualification(getQualification());
        mt.setUrl(getUrl());
        mt.setDownloadUrl(getDownloadUrl());
    
        return mt;
    }
}

