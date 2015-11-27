package model;

public class GVideo {
	
	private Snippet snippet;
	private Id id;
	private String kind;
	private String etag;
	private long viewCount;
	
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
		
		public class Thumbnails {
			private String defaultUrl;
			private High high;
			private Medium medium;
						
			public class High {
				String url;

				public String getUrl() {
					return url;
				}

				public void setUrl(String url) {
					this.url = url;
				}
			}
			
			public class Medium {
				String url;

				public String getUrl() {
					return url;
				}

				public void setUrl(String url) {
					this.url = url;
				}
			}
			
			public High getHigh() {
				return high;
			}
			public void setHigh(High high) {
				this.high = high;
			}
			public Medium getMedium() {
				return medium;
			}
			public void setMedium(Medium medium) {
				this.medium = medium;
			}
			public String getDefaultUrl() {
				return defaultUrl;
			}
			public void setDefaultUrl(String defaultUrl) {
				this.defaultUrl = defaultUrl;
			}
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

	public long getViewCount() {
		return viewCount;
	}

	public void setViewCount(long viewCount) {
		this.viewCount = viewCount;
	}

	
	@Override
	public String toString() {
		return "GVideo [snippet=" + snippet + ", id=" + id + ", kind=" + kind + ", etag=" + etag + ", viewCount="
				+ viewCount + "]";
	}

}
