import { connect } from "react-redux";
import React from "react";
import { useSelector, useDispatch } from "react-redux";
import {
  addWorkspace,
  deleteWorkspace,
  editWorkspace,
  fetchWorkspaceList,
} from "../../store/slices/workspaceSlice";
import { v4 as uuidv4 } from "uuid";
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  TextField,
} from "@mui/material";
import IconButton from "@mui/material/IconButton";
import EditIcon from "@mui/icons-material/Edit";

function AddWorkspace(props) {
  const dispatch = useDispatch();
  const [open, setOpen] = React.useState(false);

  const handleOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleSubmit = async (formData) => {
    if (props.editMode) {
      await dispatch(
        editWorkspace({
          id: props.workspace.id,
          name: formData.workspaceName,
          courseName: formData.courseName,
        }),
      );
      handleClose();
    } else {
      await dispatch(
        addWorkspace({
          id: uuidv4(),
          name: formData.workspaceName,
          repositories: [
            {
              id: uuidv4(),
              name: "Repository 1",
            },
            {
              id: uuidv4(),
              name: "Repository 2",
            },
          ],
          courseName: formData.courseName,
        }),
      );
      handleClose();
    }
  };

  const handleDelete = async () => {
    await dispatch(deleteWorkspace(props.workspace));
    handleClose();
  };

  return (
    <React.Fragment>
      {props.editMode ? (
        <IconButton onClick={handleOpen}>
          <EditIcon />
        </IconButton>
      ) : (
        <Button variant="outlined" onClick={handleOpen}>
          Add workspace
        </Button>
      )}

      <Dialog
        open={open}
        fullWidth={true}
        maxWidth="sm"
        onClose={handleClose}
        PaperProps={{
          component: "form",
          onSubmit: async (event) => {
            event.preventDefault();
            const formData = new FormData(event.currentTarget);
            const formJson = Object.fromEntries(formData.entries());

            // TODO: update with actual object
            await handleSubmit(formJson);
          },
        }}
      >
        <DialogTitle>
          {props.editMode ? "Edit workspace" : "Add workspace"}
        </DialogTitle>
        <DialogContent>
          <DialogContentText>
            {props.editMode
              ? "Edit the workspace details"
              : "Enter the workspace details"}
          </DialogContentText>
          <TextField
            autoFocus
            required
            margin="dense"
            id="workspaceName"
            name="workspaceName"
            label="Workspace Name"
            type="text"
            fullWidth
            variant="standard"
            defaultValue={props.workspace?.name}
          />
          <TextField
            margin="dense"
            id="courseName"
            name="courseName"
            label="Course Name"
            type="text"
            fullWidth
            variant="standard"
            defaultValue={props.workspace?.courseName}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleDelete}>Delete</Button>
          <Button onClick={handleClose}>Cancel</Button>
          <Button type="submit">Submit</Button>
        </DialogActions>
      </Dialog>
    </React.Fragment>
  );
}

export default connect()(AddWorkspace);
