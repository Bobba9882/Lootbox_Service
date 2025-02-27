//package com.studentgacha.lootbox.config;
//
//import com.studentgacha.lootbox.model.Item;
//import com.studentgacha.lootbox.model.Lootbox;
//import com.studentgacha.lootbox.repository.ItemRepository;
//import com.studentgacha.lootbox.repository.LootboxRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class DatabaseSeeder implements CommandLineRunner {
//
//    @Autowired
//    private LootboxRepository lootboxRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        if (lootboxRepository.findByName("Student Life Box").isEmpty()) {
//            // Create Lootboxes
//            Lootbox studentLifeBox = new Lootbox("Student Life Box", 250);
//            Lootbox transportBox = new Lootbox("Dutch Public Transport Box", 500);
//
//            // Create and link items with descriptions
//            studentLifeBox.setItems(List.of(
//                    new Item("Instant Noodles", 50, 0.40, "A lifesaver when you're too lazy to cook.", studentLifeBox),
//                    new Item("Energy Drink", 60, 0.30, "Keeps you awake for those all-nighters.", studentLifeBox),
//                    new Item("Coffee Mug", 100, 0.15, "A mug with 'I Fix Bugs' written on it, your debugging companion.", studentLifeBox),
//                    new Item("Noise-Canceling Earplugs", 120, 0.08, "Blocks out distractions in the library or dorm.", studentLifeBox),
//                    new Item("Second-Hand Study Book", 150, 0.05, "Cheaper than buying new, but still overpriced.", studentLifeBox),
//                    new Item("Cheap Bluetooth Speaker", 300, 0.01, "Perfect for background music while coding.", studentLifeBox),
//                    new Item("External Hard Drive (1TB)", 400, 0.009, "Backup your projects before they mysteriously disappear.", studentLifeBox),
//                    new Item("Mechanical Keyboard", 800, 0.005, "Clicky keys make you feel 10x more productive.", studentLifeBox),
//                    new Item("Student Discount Card", 900, 0.004, "Saves you money on food, drinks, and events.", studentLifeBox),
//                    new Item("High-End Laptop", 5000, 0.002, "The ultimate dream for every ICT student.", studentLifeBox)
//            ));
//
//            transportBox.setItems(List.of(
//                    new Item("OV-Chipkaart Sleeve", 50, 0.40, "Keeps your card safe from scratches.", transportBox),
//                    new Item("Train Delay Notification", 60, 0.30, "Mentally prepares you for an extra 30-minute wait.", transportBox),
//                    new Item("Bike Repair Kit", 120, 0.15, "Fix your bike on the go after hitting a curb.", transportBox),
//                    new Item("Cheap Foldable Umbrella", 150, 0.08, "Will break after 2 uses, but better than nothing.", transportBox),
//                    new Item("Free Coffee at the Station", 200, 0.05, "Helps you stay awake for early morning commutes.", transportBox),
//                    new Item("NS Dagkaart", 300, 0.01, "Unlimited train travel for a whole day.", transportBox),
//                    new Item("OV-Chipkaart Monthly Discount", 400, 0.009, "Cuts your travel costs significantly.", transportBox),
//                    new Item("First-Class Train Upgrade", 800, 0.005, "Enjoy luxury seating for one special trip.", transportBox),
//                    new Item("Unlimited Train Travel Weekend", 900, 0.004, "Travel anywhere in the Netherlands, stress-free.", transportBox),
//                    new Item("A Seat on the Train During Rush Hour", 5000, 0.002, "The rarest treasure of all—a free, available seat on the train at 08:00 AM. A miracle. A dream.", transportBox)
//            ));
//
//            // Save Lootboxes (cascade will save items too)
//            lootboxRepository.saveAll(List.of(studentLifeBox, transportBox));
//        }
//    }
//}
//
