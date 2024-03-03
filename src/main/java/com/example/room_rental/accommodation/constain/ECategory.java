package com.example.room_rental.accommodation.constain;

public enum ECategory {
  GUESTHOUSE(1L, "Guesthouse", "https://tourismteacher.com/wp-content/uploads/2020/08/117434950.jpg"),
  HOTELS(2L, "Hotels", "https://t2.gstatic.com/images?q=tbn:ANd9GcReeN7CmRvSG5hQdPVFWkRZh-5aMJf_L-qyFCaxSCAhzHjonuR1"),
  HOSTEL(3L, "Hostel", "https://upload.wikimedia.org/wikipedia/commons/e/e8/Hostel_Dormitory.jpg"),
  BED_AND_BREAKFAST(4L, "Bed and breakfast",
      "https://upload.wikimedia.org/wikipedia/commons/thumb/2/27/Day173abfastg.JPG/1200px-Day173abfastg.JPG"),
  COTTAGES(5L, "Cottages",
      "https://i0.wp.com/travelnolimit.com/wp-content/uploads/2020/11/holiday-home.jpg?resize=750%2C500&ssl=1"),
  APARTMENTS(6L, "Apartments",
      "https://i0.wp.com/travelnolimit.com/wp-content/uploads/2020/11/apartment-building-1149751_1920.jpg?resize=750%2C502&ssl=1"),
  CAMP(7L, "Camp", "https://t2.gstatic.com/images?q=tbn:ANd9GcQIEevihO9g8QQoAqTJ49EkJBX9S3IlKphBKENCIjEHnOI0RXJ6"),
  CAMPUS_ACCOMMODATION(8L, "Campus accommodation",
      "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcR4TyampEJQZfusmuo7zDRy8_LEkwKTEzAK4idY0Sqecn5rkzKp"),
  CHALETS(9L, "Chalets", "https://upload.wikimedia.org/wikipedia/commons/0/09/Swiss_chalet.jpg"),
  RESORT(10L, "Resort",
      "https://upload.wikimedia.org/wikipedia/commons/thumb/d/df/Town_and_Country_fh000023.jpg/800px-Town_and_Country_fh000023.jpg"),
  COTTAGE(11L, "Cottage",
      "https://t2.gstatic.com/images?q=tbn:ANd9GcRLVVM0VFl3eAAfRXp42SnKkAjXI1r2d7_1TgBKi-ziN6PxZ4Fq"),
  HOMESTAY(12L, "Homestay",
      "https://media.kookooning.com/filer_public_thumbnails/filer_public/2b/66/2b66e6e9-3d09-406d-8555-f3823df5eea3/chambrechezhabitant.jpg__600x450_q70_crop_subsampling-2_upscale.jpg"),
  ROOM(13L, "Room",
      "https://i0.wp.com/travelnolimit.com/wp-content/uploads/2020/11/private-room.jpg?resize=750%2C500&ssl=1");

  private final Long id;
  private final String name;
  private final String image;

  ECategory(Long id, String name, String image) {
    this.id = id;
    this.name = name;
    this.image = image;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getImage() {
    return image;
  }
}
