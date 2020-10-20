package com.prolog.eis.dto.wcs;

public class DoorDTO {
    private String doorNo;
    private boolean open;

    public String getDoorNo() {
        return doorNo;
    }

    public void setDoorNo(String doorNo) {
        this.doorNo = doorNo;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    @Override
    public String toString() {
        return "DoorDTO{" +
                "doorNo='" + doorNo + '\'' +
                ", open=" + open +
                '}';
    }
}
