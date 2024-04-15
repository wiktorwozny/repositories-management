import { connect } from "react-redux";
import React from "react";
import { useSelector, useDispatch } from "react-redux";
import {
  addWorkspace,
  deleteWorkspace,
  editWorkspace,
  addRepository,
  deleteRepository,
  editRepository,
} from "../../store/slices/workspaceSlice";
import AddIcon from "@mui/icons-material/Add";
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
  const [urlError, setUrlError] = React.useState(false);

  const handleOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleSubmit = async (formData) => {
    console.log("formData", formData);
    if (props.editMode) {
      await dispatch(
        editRepository({
          id: props.repository.id,
          workspaceId: props.workspace.id,
          name: formData.name,
          url: formData.url,
        }),
      );
      handleClose();
    } else {
      await dispatch(
        addRepository({
          workspaceId: props.workspace.id,
          name: formData.name,
          url: formData.url,
        }),
      );
      handleClose();
    }
  };

  const handleDelete = async () => {
    await dispatch(
      deleteRepository({
        workspaceId: props.workspace.id,
        id: props.repository.id,
      }),
    );
    handleClose();
  };

  function isGitHubRepositoryURL(url) {
    const githubRepoRegex = /^(https?:\/\/)?(www\.)?github\.com\/[\w-]+\/[\w-.]+(\/)?$/i;

    return githubRepoRegex.test(url);
  }

  const handleUrlFieldChange = (e) => {
    const url = e.target.value;

    setUrlError(!isGitHubRepositoryURL(url));
  }

  return (
    <React.Fragment>
      {props.editMode ? (
        <IconButton onClick={handleOpen}>
          <EditIcon />
        </IconButton>
      ) : (
        <IconButton onClick={handleOpen}>
          <AddIcon onClick={handleOpen} />
        </IconButton>
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
            await handleSubmit(formJson);
          },
        }}
      >
        <DialogTitle>
          {props.editMode ? "Edit repository" : "Add repository"}
        </DialogTitle>
        <DialogContent>
          <DialogContentText>
            {props.editMode
              ? "Edit the repository details"
              : "Enter the repository details"}
          </DialogContentText>
          <TextField
            autoFocus
            required
            margin="dense"
            id="name"
            name="name"
            label="Name"
            type="text"
            fullWidth
            variant="standard"
            defaultValue={props.repository?.name}
          />
          <TextField
            required
            onChange={handleUrlFieldChange}
            error={urlError}
            margin="dense"
            id="url"
            name="url"
            label="URL"
            type="text"
            fullWidth
            variant="standard"
            defaultValue={props.repository?.url}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleDelete}>Delete</Button>
          <Button onClick={handleClose}>Cancel</Button>
          <Button type="submit" disabled={urlError}>Submit</Button>
        </DialogActions>
      </Dialog>
    </React.Fragment>
  );
}

export default connect()(AddWorkspace);
