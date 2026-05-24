package io.github.guilhermebuenomartins.smarttaskscheduler.dto;

import io.github.guilhermebuenomartins.smarttaskscheduler.model.Status;
import lombok.Data;

@Data
public class TaskResponseDto extends TaskDto {
    private Integer id;
    private Status status;

    public TaskResponseDto() {
        super();
    }

    public TaskResponseDto(Integer id, Integer startTime, Status status, Integer duration, Integer priority) {
        super(startTime, duration, priority);
        this.id = id;
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        TaskResponseDto other = (TaskResponseDto) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (startTime == null) {
            if (other.startTime != null)
                return false;
        } else if (!startTime.equals(other.startTime))
            return false;
        if (status != other.status)
            return false;
        if (duration == null) {
            if (other.duration != null)
                return false;
        } else if (!duration.equals(other.duration))
            return false;
        if (priority == null) {
            if (other.priority != null)
                return false;
        } else if (!priority.equals(other.priority))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((duration == null) ? 0 : duration.hashCode());
        result = prime * result + ((priority == null) ? 0 : priority.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "TaskResponseDto [id=" + id + ", startTime=" + startTime + ", status=" + status + ", priority="
                + priority + ", duration=" + duration + "]";
    }
}
