public class Runner {
    public void run(String[] args){
        try {
            FileWork fileWork = switch (args.length) {
                case 0 -> {
                    CLI client = new CLI();
                    yield client.getFileWorkWithArgs();
                }
                case 3 -> new FileWork(args[0], args[1], args[2]);
                case 2 -> {
                    if(args[0].equals("BRUTE_FORCE")) { yield new FileWork(args[0], args[1], "");
                    }else  throw new Exception("Перевірте кількість вхідних параметрів");
                }
                default -> throw new Exception("Перевірте кількість вхідних параметрів");
            };

            fileWork.readAndWrite();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}