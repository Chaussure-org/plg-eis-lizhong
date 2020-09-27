package com.prolog.eis.dto.enginee;

public class KuCunTotalDto {

	private int spId;	//商品批次Id
	
	private int num;		//数量
	

	public int getSpId() {
		return spId;
	}

	public void setSpId(int spId) {
		this.spId = spId;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + spId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
		if (obj == null) {
            return false;
        }
		if (getClass() != obj.getClass()) {
            return false;
        }
		KuCunTotalDto other = (KuCunTotalDto) obj;
	        if (spId == 0) {
	            if (other.spId != 0) {
                    return false;
                }
	        } else if (spId!=other.spId ) {
                return false;
            }
			
		return true;
	}
	public KuCunTotalDto() {
		super();
	}
	public KuCunTotalDto(int spId, int num) {
		super();
		this.spId = spId;
		this.num = num;
	}

	public static KuCunTotalDto merge(KuCunTotalDto s1, KuCunTotalDto s2) {
        if (!s1.equals ( s2 )) {
            throw new IllegalArgumentException ();
        }
        return new KuCunTotalDto( s1.spId, s1.num + s2.num );
    }


	
}
