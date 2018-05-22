package deadline.realm;

import deadline.annotation.apt.RealmField;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@RealmField
public class ShopRealm extends RealmObject {

    @PrimaryKey
    private long id;
    private long company_id;
    private Long agent_id;
    private String name;
    private int type;
    private int status;
    private int expire;
    private String region;
    private String remark;
    private String sys_remark;

    //对应的仓库 ID
    private long warehouseId;
    //对应的商品分组 ID
    private RealmList<Long> productGroupIds;

    private long created_at;
    private long updated_at;
    private long deleted_at;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSys_remark() {
        return sys_remark;
    }

    public void setSys_remark(String sys_remark) {
        this.sys_remark = sys_remark;
    }

    public long getCompany_id() {
        return company_id;
    }

    public void setCompany_id(long company_id) {
        this.company_id = company_id;
    }

    public Long getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(Long agent_id) {
        this.agent_id = agent_id;
    }

    public long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public RealmList<Long> getProductGroupIds() {
        return productGroupIds;
    }

    public void setProductGroupIds(RealmList<Long> productGroupIds) {
        this.productGroupIds = productGroupIds;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public long getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(long updated_at) {
        this.updated_at = updated_at;
    }

    public long getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(long deleted_at) {
        this.deleted_at = deleted_at;
    }
}
