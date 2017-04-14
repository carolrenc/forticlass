package youtubeAPI;
public class Video {
		String title;
		String description;
		String tags[]; 
		int categoryId;
		
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String[] getTags() {
			return tags;
		}
		public void setTags(String[] tags) {
			this.tags = tags;
		}
		public int getCategoryId() {
			return categoryId;
		}
		public void setCategoryId(int categoryId) {
			this.categoryId = categoryId;
		}
		
		

}
