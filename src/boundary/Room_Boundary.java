package boundary;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.GuestMrg;
import controller.ReservationMrg;
import controller.RoomMrg;
import entity.Guest;
import entity.Reservation;
import entity.Room;
import entity.Room.BedType;
import entity.Room.RoomType;

public class Room_Boundary {

	private Scanner sc = new Scanner(System.in);
	private RoomMrg roomMrg = RoomMrg.getInstance();
	private Room room = RoomMrg.createNewRoom();

	public void roomMain() {
		String choice;
		do {
			System.out.println("Room System\n" + "0. Return to Main Menu\n" + "1. Create Room\n" + "2. Update Room\n"
					+ "3. Search Room\n" + "4. Check In\n" + "5. Check Out\n" + "6. Print Room Status Report\n");
			choice = sc.nextLine();

			switch (choice) {
			case "0":
				break;
			case "1":
				createRoomMenu();
				break;
			case "2":
				updateRoomMenu();
				break;
			case "3":
				searchRoomMenu();
				break;
			case "4":
				checkInMenu();
				break;
			case "5":
				checkOutMenu();
			case "6":
				roomMrg.getRoomReportMenu();
			}
		} while (!choice.equalsIgnoreCase("0"));
		sc.close();
	}

	private void createRoomMenu() {
		Character confirm;

		System.out.println("Create Room");
		enterRoomNum();
		enterRoomType();
		enterBedType();
		enterFacing();
		enterWeekdayRate();
		enterWeekendRate();
		enterAllowSmoking();
		enterHasWifi();
		room.setRoomStatus(Room.RoomStatus.VACANT);
		do {
			room.printRoomInfo();
			System.out.println("Press Y to confirm," + "N to discard and " + "(No.) to edit a field.");
			confirm = sc.nextLine().toUpperCase().charAt(0);
			switch (confirm) {
			case 'Y':
				roomMrg.createRoom(room);
				break;
			case 'N':
				break;
			case '1':
				enterRoomNum();
				break;
			case '2':
				enterRoomType();
				break;
			case '3':
				enterBedType();
				break;
			case '4':
				enterFacing();
				break;
			case '5':
				enterWeekdayRate();
				break;
			case '6':
				enterWeekendRate();
				break;
			case '7':
				enterAllowSmoking();
				break;
			case '8':
				enterHasWifi();
				break;
			default:
				break;
			}
		} while (!(confirm.equals('Y') || confirm.equals('N')));

	}

	private void searchRoomMenu() {
		String i;
		do {
			System.out.println("Room System\n" + "0. Return to Main Menu\n" + "1. Search Room by Room number\n"
					+ "2. Search Room by Guest name\n");

			i = sc.nextLine();
			switch (i) {
			case "0":
				break;
			case "1":
				searchRoomByRoomNumMenu();
				break;
			case "2":
				searchRoomByGuestNameMenu();
				break;
			}
		} while (!i.equalsIgnoreCase("0"));
	}

	private void checkInMenu() {
		System.out.println("Room System\n" + "0. Return to Main Menu\n" + "1. Walk In \n" + "2. Reservation\n");
		String i;
		do {
			i = sc.nextLine();
			switch (i) {
			case "0":
				break;
			case "1":
				// WalkIncheckInMenu();
				break;
			case "2":
				reservationCheckInMenu();
				break;
			}
		} while (!i.equalsIgnoreCase("0"));
	}

	private void updateRoomMenu() {
		String i;
		do {
			System.out.println("Room System\n" + "0. Return to Main Menu\n" + "1. Update Room details\n"
					+ "2. Update Room Status\n");
			i = sc.nextLine();

			switch (i) {
			case "0":
				break;
			case "1":
				updateRoomBydetailsMenu();
				break;
			case "2":
				updateRoomStatusMenu();
				break;
			}
		} while (!i.equalsIgnoreCase("0"));
	}

	private void updateRoomBydetailsMenu() {
		System.out.println("update Room");
		System.out.println("Enter room number : ");
		String roomNum = sc.nextLine();
		room = roomMrg.searchRoomByNum(roomNum);
		if (room != null) {
			Character confirm;
			do {
				room.printRoomInfo();
				System.out.println("Press Y to confirm," + "N to discard and "
						+ "(No.) to edit a field.(Unable to edit Room Number)");
				confirm = sc.nextLine().charAt(0);
				switch (confirm) {
				case 'Y':
					boolean success = roomMrg.updateRoom(room);
					if (success) {
						System.out.println("Sucessfully update room");
					} else {
						System.out.println("Unable to update room");
					}
					break;
				case 'N':
					break;
				case '2':
					enterRoomType();
					break;
				case '3':
					enterBedType();
					break;
				case '4':
					enterFacing();
					break;
				case '5':
					enterWeekdayRate();
					break;
				case '6':
					enterWeekendRate();
					break;
				case '7':
					enterAllowSmoking();
					break;
				case '8':
					enterHasWifi();
					break;
				default:
					break;
				}
			} while (!(confirm.equals('Y') || confirm.equals('N')));
		}else {
			System.out.println("There are no room existed by this room number");
		}

	}

	private void updateRoomStatusMenu() {
		System.out.println("update Room");
		System.out.println("Enter room number : ");
		String roomNum = sc.nextLine();
		room = roomMrg.searchRoomByNum(roomNum);
		if (room != null) {
			Character confirm;

			do {
				System.out.println("Enter Room Status: VACANT, OCCUPIED, RESERVED, MAINTENANCE ");
				String status = sc.nextLine();
				if (status.equalsIgnoreCase("VACANT") || status.equalsIgnoreCase("OCCUPIED")
						|| status.equalsIgnoreCase("RESERVED") || status.equalsIgnoreCase("MAINTENANCE")) {
					if (status.equalsIgnoreCase("MAINTENANCE")) {
						status = "UNDER_MAINTENANCE";
					}
					room.setRoomStatus(RoomMrg.strToRoomStatus(status));
					break;
				} else {
					System.out.println("Please enter the correct Status!");
				}
			} while (true);

			do {
				room.printRoomInfo();
				System.out.println("Press Y to confirm," + "N to discard");
				confirm = sc.nextLine().toUpperCase().charAt(0);
				switch (confirm) {
				case 'Y':
					boolean success = roomMrg.updateRoom(room);
					if (success) {
						System.out.println("Sucessfully update room");
					} else {
						System.out.println("Unable to update room");
					}
					break;
				case 'N':
					break;
				default:
					break;
				}
			} while (!(confirm.equals('Y') || confirm.equals('N')));
		}

	}

	private void searchRoomByRoomNumMenu() {
		System.out.println("Enter room number: ");
		String roomNum = sc.nextLine();
		room = roomMrg.searchRoomByNum(roomNum);
		room.printRoomInfo();
		String ic = ReservationMrg.getInstance().getReservationByCode(room.getReservationCode()).getGuestIC();
		Guest g = GuestMrg.getInstance().searchGuestByIC(ic);
		System.out.println("Guest Name : " + g.getGuestName());
	}

	public void searchRoomByGuestNameMenu() {

		System.out.println("Enter Guest Name: ");
		String name = sc.nextLine();
		List<Room> roomList = roomMrg.searchRoomByGuestName(name);
		if (roomList.size() > 0) {
			for (Room r : roomList) {
				r.printRoomInfo();
			}
		} else {
			System.out.println("No room found by the name " + name);
		}

	}

	public void reservationCheckInMenu() {
		System.out.println("Please enter the reservation code: ");
		String reservationCode = sc.nextLine();
		Reservation reservation = ReservationMrg.getInstance().getReservationByCode(reservationCode);
		if (reservation != null) {
			if (reservation.getReservationStatus().equals(Reservation.ReservationStatus.CONFIRMED)) {
				roomMrg.checkInReservedRoom(reservation);
				System.out.println("Sucessfully check in to the room");
			} else {
				System.out.println("Reservation is on " + reservation.getReservationStatus());
			}
		} else {
			System.out.println("Reservation not found");
		}

	}

	public void checkOutMenu() {
		System.out.println("Please enter Guest IC: ");
		String ic = sc.nextLine();
		Guest guest = GuestMrg.getInstance().searchGuestByIC(ic);
		if (guest != null) {
			if (guest.getRoomNumList() != null && guest.getRoomNumList().size() > 0) {
				String reservationCode = null;
				for (String roomNum : guest.getRoomNumList()) {
					Room r = roomMrg.searchRoomByNum(roomNum);
					reservationCode = r.getReservationCode();
					roomMrg.updateRoom(r, null, null, null, Room.RoomStatus.VACANT);
				}
				Payment_Boundary.getInstance().paymentMain(reservationCode);

			} else {
				System.out.println("There are no rooms being occupied by this user");
			}
		} else {
			System.out.println("Guest does not exist");
		}
	}

	private void enterRoomNum() {
		do {
			System.out.println("Enter room number: ");
			String roomNum = sc.nextLine();
			if (roomNum.matches("^[0-9]*$")) {
				Room r = roomMrg.searchRoomByNum(roomNum);
				if (r == null) {
					room.setRoomNumber(roomNum);
					break;
				} else {
					System.out.println("Room number already exist");
				}
			} else {
				System.out.println("Please enter room number in digits");
			}
		} while (true);
	}

	private void enterRoomType() {
		do {
			System.out.println("Enter room type: SINGLE, DOUBLE, DELUXE, VIP  ");
			String roomType = sc.nextLine();
			if (roomType.equalsIgnoreCase("SINGLE") || roomType.equalsIgnoreCase("DOUBLE")
					|| roomType.equalsIgnoreCase("DELUXE") || roomType.equalsIgnoreCase("VIP")) {
				room.setRoomType(RoomMrg.strToRoomType(roomType));
				break;
			} else {
				System.out.println("Please enter the correct room type!");
			}
		} while (true);

	}

	private void enterBedType() {
		do {
			System.out.println("Enter bed type: SINGLE, DOUBLE, KING ");
			String bedType = sc.nextLine();
			if (bedType.equalsIgnoreCase("SINGLE") || bedType.equalsIgnoreCase("DOUBLE")
					|| bedType.equalsIgnoreCase("KING")) {
				room.setBedType(RoomMrg.strToBedType(bedType));
				break;
			} else {
				System.out.println("Please enter the correct bed type!");
			}
		} while (true);

	}

	private void enterFacing() {
		do {
			System.out.println("Enter facing: NORTH, SOUTH, EAST, WEST ");
			String facing = sc.nextLine();
			if (facing.equalsIgnoreCase("NORTH") || facing.equalsIgnoreCase("SOUTH") || facing.equalsIgnoreCase("EAST")
					|| facing.equalsIgnoreCase("WEST")) {
				room.setFacing(RoomMrg.strToFacing(facing));
				break;
			} else {
				System.out.println("Please enter the correct facing!");
			}
		} while (true);

	}

	private void enterWeekdayRate() {
		do {
			System.out.println("Enter weekday rate: ");
			if (sc.hasNextDouble()) {
				room.setRoomRateWeekday(sc.nextDouble());
				sc.nextLine();
				break;
			} else {
				System.out.println("Please enter the correct pricing!");
				sc.nextLine();
			}
		} while (true);

	}

	private void enterWeekendRate() {
		do {
			System.out.println("Enter weekend rate: ");
			if (sc.hasNextDouble()) {
				room.setRoomRateWeekend(sc.nextDouble());
				sc.nextLine();
				break;
			} else {
				System.out.println("Please enter the correct pricing!");
				sc.nextLine();
			}
		} while (true);

	}

	private void enterAllowSmoking() {
		boolean bool;
		do {
			System.out.println("Enter allow smoking: (Y/N) ");
			String input = sc.nextLine();
			if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("N")) {
				if (input.equalsIgnoreCase("Y")) {
					bool = true;
				} else {
					bool = false;
				}
				room.setSmoking(bool);
				break;
			} else {
				System.out.println("Please enter Y/N ");
			}
		} while (true);
	}

	private void enterHasWifi() {
		boolean bool;
		do {
			System.out.println("Enter has wifi: (Y/N) ");
			String input = sc.nextLine();
			if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("N")) {
				if (input.equalsIgnoreCase("Y")) {
					bool = true;
				} else {
					bool = false;
				}
				room.setWifi(bool);
				break;
			} else {
				System.out.println("Please enter Y/N ");
			}
		} while (true);
	}

	public static void main(String[] args) {
		try {
			RoomMrg.getInstance().loadRoomData();
			GuestMrg.getInstance().loadGuestData();
			ReservationMrg.getInstance().loadReservationData();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Room_Boundary room_Boundary = new Room_Boundary();
		RoomMrg roomMrg = new RoomMrg();
		roomMrg.getRoomReportMenu();
		room_Boundary.roomMain();

	}
}
