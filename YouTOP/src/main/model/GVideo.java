package model;

public class GVideo {
	
	private Snippet snippet;
	private Id id;
	private String kind;
	private String etag;
	private String viewCount;
	
	public class Snippet {
		private String publishedAt;
		private String description;
		private Thumbnails thumbnails;
		private String title;
		private String channelId;
		private String channelTitle;
		private String liveBroadcastContent;
		

		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public Thumbnails getThumbnails() {
			return thumbnails;
		}
		public void setThumbnails(Thumbnails thumbnails) {
			this.thumbnails = thumbnails;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getChannelId() {
			return channelId;
		}
		public void setChannelId(String channelId) {
			this.channelId = channelId;
		}
		public String getChannelTitle() {
			return channelTitle;
		}
		public void setChannelTitle(String channelTitle) {
			this.channelTitle = channelTitle;
		}
		public String getLiveBroadcastContent() {
			return liveBroadcastContent;
		}
		public void setLiveBroadcastContent(String liveBroadcastContent) {
			this.liveBroadcastContent = liveBroadcastContent;
		}
		public String getPublishedAt() {
			return publishedAt;
		}
		public void setPublishedAt(String publishedAt) {
			this.publishedAt = publishedAt;
		}
		
	}
	
	public class Thumbnails {
		private DefaultDef defaultDef;
		private HighDef highDef;
		private MediumDef mediumDef;
		
		public class DefaultDef {
			String url;

			public String getUrl() {
				return url;
			}

			public void setUrl(String url) {
				this.url = url;
			}
		}
		
		public class HighDef {
			String url;

			public String getUrl() {
				return url;
			}

			public void setUrl(String url) {
				this.url = url;
			}
		}
		
		public class MediumDef {
			String url;

			public String getUrl() {
				return url;
			}

			public void setUrl(String url) {
				this.url = url;
			}
		}
		
		public DefaultDef getDefaultDef() {
			return defaultDef;
		}
		public void setDefaultDef(DefaultDef defaultDef) {
			this.defaultDef = defaultDef;
		}
		public HighDef getHighDef() {
			return highDef;
		}
		public void setHighDef(HighDef highDef) {
			this.highDef = highDef;
		}
		public MediumDef getMediumDef() {
			return mediumDef;
		}
		public void setMediumDef(MediumDef mediumDef) {
			this.mediumDef = mediumDef;
		}
	}
	
	public class Id {
		private String kind;
		private String videoId;
		
		public String getKind() {
			return kind;
		}
		public void setKind(String kind) {
			this.kind = kind;
		}
		public String getVideoId() {
			return videoId;
		}
		public void setVideoId(String videoId) {
			this.videoId = videoId;
		}
	}

	
	public Snippet getSnippet() {
		return snippet;
	}

	public void setSnippet(Snippet snippet) {
		this.snippet = snippet;
	}

	public Id getId() {
		return id;
	}

	public void setId(Id id) {
		this.id = id;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getEtag() {
		return etag;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}

	public String getViewCount() {
		return viewCount;
	}

	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
	}

}
