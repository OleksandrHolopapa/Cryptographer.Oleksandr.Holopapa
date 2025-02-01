public class Main {
    public static void main(String[] args) {
        CLI client = args.length!=0? new CLI(args): new CLI();
        client.setVisible(true);
    }
}