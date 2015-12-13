package model;

import java.util.ArrayList;

public class GVideo {
	
	private String etag;
	private String id;
	private String kind;
	private Snippet snippet;
	private long viewCount;

	public class Snippet {
		private int categoryId;
		private String channelId;
		private String channelTitle;
		private String description;
		private String liveBroadcastContent;
		private Localized localized;
		private String publishedAt;
		private ArrayList<String> tags;
		private Thumbnails thumbnails;
		private String title;
		
		public class Localized {
			private String description;
			private String title;
			public String getDescription() {
				return description;
			}
			public void setDescription(String description) {
				this.description = description;
			}
			public String getTitle() {
				return title;
			}
			public void setTitle(String title) {
				this.title = title;
			}
		}
		
		public class Thumbnails {
			private String defaultUrl;

			public String getDefaultUrl() {
				return defaultUrl;
			}

			public void setDefaultUrl(String defaultUrl) {
				this.defaultUrl = defaultUrl;
			}
		}

		public int getCategoryId() {
			return categoryId;
		}

		public void setCategoryId(int categoryId) {
			this.categoryId = categoryId;
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

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getLiveBroadcastContent() {
			return liveBroadcastContent;
		}

		public void setLiveBroadcastContent(String liveBroadcastContent) {
			this.liveBroadcastContent = liveBroadcastContent;
		}

		public Localized getLocalized() {
			return localized;
		}

		public void setLocalized(Localized localized) {
			this.localized = localized;
		}

		public String getPublishedAt() {
			return publishedAt;
		}

		public void setPublishedAt(String publishedAt) {
			this.publishedAt = publishedAt;
		}

		public ArrayList<String> getTags() {
			return tags;
		}

		public void setTags(ArrayList<String> tags) {
			this.tags = tags;
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
	}

	public String getEtag() {
		return etag;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public Snippet getSnippet() {
		return snippet;
	}

	public void setSnippet(Snippet snippet) {
		this.snippet = snippet;
	}

	public long getViewCount() {
		return viewCount;
	}

	public void setViewCount(long viewCount) {
		this.viewCount = viewCount;
	}
}
